package com.ccc.nameapp.widgets.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.ccc.nameapp.R
import kotlin.math.min

class CircleView : View {

    private var mPaint: Paint? = null

    private var mCircleColor = DEFAULT_CIRCLE_COLOR
        set(circleColor) {
            field = circleColor
            invalidate()
        }

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        val usableWidth = width - (paddingLeft + paddingRight)
        val usableHeight = height - (paddingTop + paddingBottom)

        val radius = min(usableWidth, usableHeight) / 2
        val circleX = paddingLeft + usableWidth / 2
        val circleY = paddingTop + usableHeight / 2

        mPaint?.let { paint ->
            paint.color = mCircleColor
            canvas.drawCircle(circleX.toFloat(), circleY.toFloat(), radius.toFloat(), paint)
        }
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        mCircleColor = typedArray.getColor(R.styleable.CircleView_circleColor, 0)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        typedArray.recycle()
    }

    companion object {
        const val DEFAULT_CIRCLE_COLOR = Color.WHITE
    }
}
