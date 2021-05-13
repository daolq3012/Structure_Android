package com.ccc.nameapp.widgets.view

import android.content.Context
import android.graphics.RectF
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.ColorFilter
import android.graphics.Shader
import android.graphics.Rect
import android.graphics.Outline
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.ccc.nameapp.R
import kotlin.math.min

class CircleImageView : AppCompatImageView {

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()

    private val mShaderMatrix = Matrix()
    private val mBitmapPaint = Paint()
    private val mBorderPaint = Paint()
    private val mCircleBackgroundPaint = Paint()

    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mCircleBackgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR

    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null
    private var mBitmapWidth: Int = 0
    private var mBitmapHeight: Int = 0

    private var mDrawableRadius: Float = 0.toFloat()
    private var mBorderRadius: Float = 0.toFloat()

    private var mColorFilter: ColorFilter? = null

    private var mReady: Boolean = false
    private var mSetupPending: Boolean = false
    private var mBorderOverlay: Boolean = false
    private var isDisableCircularTransformation: Boolean = false
        set(disableCircularTransformation) {
            if (isDisableCircularTransformation == disableCircularTransformation) {
                return
            }

            field = disableCircularTransformation
            initializeBitmap()
        }

    private var borderColor: Int
        get() = mBorderColor
        set(@ColorInt borderColor) {
            if (borderColor == mBorderColor) {
                return
            }

            mBorderColor = borderColor
            mBorderPaint.color = mBorderColor
            invalidate()
        }

    private var circleBackgroundColor: Int
        get() = mCircleBackgroundColor
        set(@ColorInt circleBackgroundColor) {
            if (circleBackgroundColor == mCircleBackgroundColor) {
                return
            }

            mCircleBackgroundColor = circleBackgroundColor
            mCircleBackgroundPaint.color = circleBackgroundColor
            invalidate()
        }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     */
    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     */
    var fillColor: Int
        @Deprecated("Use {@link #getCircleBackgroundColor()} instead.")
        get() = circleBackgroundColor
        @Deprecated("Use {@link #setCircleBackgroundColor(int)} instead.")
        set(@ColorInt fillColor) {
            circleBackgroundColor = fillColor
        }

    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            if (borderWidth == mBorderWidth) {
                return
            }

            mBorderWidth = borderWidth
            setup()
        }

    var isBorderOverlay: Boolean
        get() = mBorderOverlay
        set(borderOverlay) {
            if (borderOverlay == mBorderOverlay) {
                return
            }

            mBorderOverlay = borderOverlay
            setup()
        }

    constructor(context: Context) : super(context) {

        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(
        context, attrs, defStyle
    ) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)

        mBorderWidth = a.getDimensionPixelSize(
            R.styleable.CircleImageView_civ_border_width,
            DEFAULT_BORDER_WIDTH
        )
        mBorderColor =
            a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay = a.getBoolean(
            R.styleable.CircleImageView_civ_border_overlay,
            DEFAULT_BORDER_OVERLAY
        )

        // Look for deprecated civ_fill_color if civ_circle_background_color is not set
        if (a.hasValue(R.styleable.CircleImageView_civ_circle_background_color)) {
            mCircleBackgroundColor = a.getColor(
                R.styleable.CircleImageView_civ_circle_background_color,
                DEFAULT_CIRCLE_BACKGROUND_COLOR
            )
        } else if (a.hasValue(R.styleable.CircleImageView_civ_fill_color)) {
            mCircleBackgroundColor = a.getColor(
                R.styleable.CircleImageView_civ_fill_color,
                DEFAULT_CIRCLE_BACKGROUND_COLOR
            )
        }

        a.recycle()

        init()
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = OutlineProvider()
        }

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) { String.format("ScaleType %s not supported.", scaleType) }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (isDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }

        if (mBitmap == null) {
            return
        }

        if (mCircleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(
                mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius,
                mCircleBackgroundPaint
            )
        }
        canvas.drawCircle(
            mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius,
            mBitmapPaint
        )
        if (mBorderWidth > 0) {
            canvas.drawCircle(
                mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius,
                mBorderPaint
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    @Deprecated(
        "Use {@link #setBorderColor(int)} instead",
        ReplaceWith("borderColor = context.resources.getColor(borderColorRes)")
    )
    fun setBorderColorResource(@ColorRes borderColorRes: Int) {
        borderColor = ContextCompat.getColor(context, borderColorRes)
    }

    private fun setCircleBackgroundColorResource(@ColorRes circleBackgroundRes: Int) {
        circleBackgroundColor = ContextCompat.getColor(context, circleBackgroundRes)
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     * drawn behind the drawable
     */
    @Deprecated(
        "Use {@link #setCircleBackgroundColorResource(int)} instead.",
        ReplaceWith("setCircleBackgroundColorResource(fillColorRes)")
    )
    fun setFillColorResource(@ColorRes fillColorRes: Int) {
        setCircleBackgroundColorResource(fillColorRes)
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }

        mColorFilter = cf
        applyColorFilter()
        invalidate()
    }

    override fun getColorFilter(): ColorFilter? {
        return mColorFilter
    }

    private fun applyColorFilter() {
        mBitmapPaint.colorFilter = mColorFilter
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight, BITMAP_CONFIG
                )
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: OutOfMemoryError) {
            Log.e(TAG, "getBitmapFromDrawable: ", e)
            return null
        }
    }

    private fun initializeBitmap() {
        mBitmap = if (isDisableCircularTransformation) {
            null
        } else {
            getBitmapFromDrawable(drawable)
        }
        setup()
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }

        if (width == 0 && height == 0) {
            return
        }

        if (mBitmap == null) {
            invalidate()
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()

        mCircleBackgroundPaint.style = Paint.Style.FILL
        mCircleBackgroundPaint.isAntiAlias = true
        mCircleBackgroundPaint.color = mCircleBackgroundColor

        mBitmapHeight = mBitmap!!.height
        mBitmapWidth = mBitmap!!.width

        mBorderRect.set(calculateBounds())
        mBorderRadius = min(
            (mBorderRect.height() - mBorderWidth) / 2.0f,
            (mBorderRect.width() - mBorderWidth) / 2.0f
        )

        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        mDrawableRadius = min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)

        applyColorFilter()
        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom

        val sideLength = min(availableWidth, availableHeight)

        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + mDrawableRect.left,
            (dy + 0.5f).toInt() + mDrawableRect.top
        )

        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private inner class OutlineProvider : ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            val bounds = Rect()
            mBorderRect.roundOut(bounds)
            outline.setRoundRect(bounds, bounds.width() / 2.0f)
        }
    }

    companion object {

        private const val TAG = "CircleImageView"

        private val SCALE_TYPE = ScaleType.CENTER_CROP

        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_DIMENSION = 2

        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT
        private const val DEFAULT_BORDER_OVERLAY = false
    }
}
