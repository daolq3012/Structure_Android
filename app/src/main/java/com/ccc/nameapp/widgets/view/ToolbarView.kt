package com.ccc.nameapp.widgets.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.ccc.nameapp.R
import com.ccc.nameapp.extension.invisible
import com.ccc.nameapp.extension.visible
import com.ccc.nameapp.utils.DimensionUtils

class ToolbarView : ConstraintLayout {
    private lateinit var mBackImageView: ImageView
    private lateinit var mMenuImageView: ImageView
    private lateinit var mTitleTextView: TextView
    private lateinit var mHorizontalLineView: View

    @DrawableRes
    private var mBackImageResId: Int? = null

    @DrawableRes
    private var mMenuImageResId: Int? = null
    private var mLineColor: Int = ContextCompat.getColor(context, R.color.colorLittleBlack)
    private var mTitleColor: Int = ContextCompat.getColor(context, R.color.colorLittleBlack)
    private var mTitleSize: Float =
        DimensionUtils.getDimensionWithScaledDensity(context, R.dimen.sp_18)
    private var mTitleContent: String = ""
    private var mTitleStyle: Int = Typeface.BOLD
    private var mMenuHelper: MenuPopupHelper? = null
    private var mOnMenuItemClickListener: OnMenuItemClickListener? = null
    private var mOnBackClickListener: OnBackClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView, 0, 0)
        try {
            mBackImageResId =
                typeArray.getResourceId(R.styleable.ToolbarView_backImage, R.drawable.ic_back)
            mMenuImageResId =
                typeArray.getResourceId(R.styleable.ToolbarView_menuImage, R.drawable.ic_menu)
            mLineColor = typeArray.getColor(
                R.styleable.ToolbarView_lineColor,
                ContextCompat.getColor(context, R.color.colorLittleBlack)
            )
            mTitleColor = typeArray.getColor(
                R.styleable.ToolbarView_titleColor,
                ContextCompat.getColor(context, R.color.colorLittleBlack)
            )
            mTitleSize =
                typeArray.getDimension(
                    R.styleable.ToolbarView_titleSize,
                    DimensionUtils.getDimensionWithScaledDensity(context, R.dimen.sp_18)
                )
            mTitleContent = typeArray.getString(R.styleable.ToolbarView_titleContent) ?: ""
            mTitleStyle = typeArray.getInt(R.styleable.ToolbarView_titleStyle, Typeface.BOLD)
        } finally {
            typeArray.recycle()
        }
        initViews()
        constraintViews()
        handleEvents()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    @SuppressLint("RestrictedApi")
    fun inflateToolbarPopupMenu(context: Context, @MenuRes menuResId: Int) {
        val popupMenu = PopupMenu(context, mMenuImageView)
        val menuInflater = popupMenu.menuInflater
        menuInflater.inflate(menuResId, popupMenu.menu)
        mMenuHelper = MenuPopupHelper(context, popupMenu.menu as MenuBuilder, mMenuImageView)
        mMenuHelper?.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            mOnMenuItemClickListener?.onMenuItemClick(it)
            true
        }
    }

    fun setOnMenuItemClickListener(onMenuItemClickListener: OnMenuItemClickListener?) {
        mOnMenuItemClickListener = onMenuItemClickListener
    }

    fun setOnBackClickListener(onBackClickListener: OnBackClickListener?) {
        mOnBackClickListener = onBackClickListener
    }

    fun showMenu() {
        mMenuImageView.visible()
        mMenuImageView.isEnabled = true
    }

    fun hideBack() {
        mBackImageView.invisible()
        mBackImageView.isEnabled = false
    }

    fun hideTitle() {
        mTitleTextView.invisible()
        mTitleTextView.isEnabled = false
    }

    fun hideLine() {
        mHorizontalLineView.invisible()
    }

    private fun initViews() {
        mBackImageView = ImageView(context).apply {
            setPadding(DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24).toInt())
            mBackImageResId?.let { setImageResource(it) }
        }
        mMenuImageView = ImageView(context).apply {
            setPadding(DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_24).toInt())
            mMenuImageResId?.let { setImageResource(it) }
        }
        hideMenu()
        mTitleTextView = TextView(context).apply {
            text = mTitleContent
            gravity = Gravity.CENTER
            setTextColor(mTitleColor)
            textSize = mTitleSize
            setTypeface(null, mTitleStyle)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            val layoutParams = ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
            this.layoutParams = layoutParams
        }
        mHorizontalLineView = View(context).apply {
            setBackgroundColor(mLineColor)
            val layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_1).toInt()
                )
            this.layoutParams = layoutParams
        }
    }

    private fun constraintViews() {
        val constraintSet = ConstraintSet()

        // anchor backImageView to parentView
        mBackImageView.id = View.generateViewId()
        addView(mBackImageView)
        constraintSet.clone(this)
        constraintSet.connect(mBackImageView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(mBackImageView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(
            mBackImageView.id,
            ConstraintSet.LEFT,
            id,
            ConstraintSet.LEFT,
            DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt()
        )
        constraintSet.applyTo(this)

        // anchor menuImageView to parentView
        mMenuImageView.id = View.generateViewId()
        addView(mMenuImageView)
        constraintSet.clone(this)
        constraintSet.connect(
            mMenuImageView.id,
            ConstraintSet.TOP,
            mBackImageView.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            mMenuImageView.id,
            ConstraintSet.BOTTOM,
            mBackImageView.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            mMenuImageView.id,
            ConstraintSet.RIGHT,
            id,
            ConstraintSet.RIGHT,
            DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt()
        )
        constraintSet.applyTo(this)

        // anchor titleTextView to parentView
        mTitleTextView.id = View.generateViewId()
        addView(mTitleTextView)
        constraintSet.clone(this)
        constraintSet.connect(mTitleTextView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        constraintSet.connect(mTitleTextView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        constraintSet.connect(
            mTitleTextView.id,
            ConstraintSet.LEFT,
            mBackImageView.id,
            ConstraintSet.RIGHT,
            DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt()
        )
        constraintSet.connect(
            mTitleTextView.id,
            ConstraintSet.RIGHT,
            mMenuImageView.id,
            ConstraintSet.LEFT,
            DimensionUtils.getDimensionWithDensity(context, R.dimen.dp_16).toInt()
        )
        constraintSet.applyTo(this)

        // anchor HorizontalLineView to parentView
        mHorizontalLineView.id = View.generateViewId()
        addView(mHorizontalLineView)
        constraintSet.clone(this)
        constraintSet.connect(
            mHorizontalLineView.id,
            ConstraintSet.BOTTOM,
            id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(mHorizontalLineView.id, ConstraintSet.LEFT, id, ConstraintSet.LEFT)
        constraintSet.connect(mHorizontalLineView.id, ConstraintSet.RIGHT, id, ConstraintSet.RIGHT)
        constraintSet.applyTo(this)
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvents() {
        mMenuImageView.setOnClickListener { mMenuHelper?.show() }
        mBackImageView.setOnClickListener { mOnBackClickListener?.onBackItemClick() }
    }

    private fun hideMenu() {
        mMenuImageView.invisible()
        mMenuImageView.isEnabled = false
    }

    interface OnMenuItemClickListener {
        fun onMenuItemClick(menuItem: MenuItem)
    }

    interface OnBackClickListener {
        fun onBackItemClick()
    }
}
