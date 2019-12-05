package com.liushx.corelibrary.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.liushx.corelibrary.R
import com.liushx.corelibrary.utils.DisplayUtils

/**
 * 半圆形仪表盘
 */
class CircleDashboard : View {
    private val defaultBgColor = ContextCompat.getColor(context, R.color.base_light_gray)
    private val defaultProgressColor = ContextCompat.getColor(context, R.color.colorPrimary)
    private val defaultPadding =
        DisplayUtils.dpToPx(8f)
    private val defaultRadius =
        DisplayUtils.dpToPx(80f)
    private val defaultBorderWidth =
        DisplayUtils.dpToPx(10f)
    private val defaultDialsLength =
        DisplayUtils.dpToPx(10f)

    private val defaultViewSize =
        DisplayUtils.dpToPx(10f)

    private var bgPaint = Paint()
    private var dialsPaint = Paint()
    private var textPaint = TextPaint()

    private var borderWidth = defaultBorderWidth
    private var bgColor = defaultBgColor
    private var progressColor = defaultProgressColor
    private var maxPadding = defaultPadding
    private var bigDialsLength = defaultDialsLength
    private var smallDialsLength = defaultDialsLength * 0.6f
    private var radius = defaultRadius
    private var viewSize = defaultViewSize


    private val rectF = RectF()

    private val maxValue = 100f
    private var currentValue = 35f

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context!!)
    }


    private fun init(context: Context) {

        val ta = context.theme.obtainStyledAttributes(R.styleable.CircleDashboard)
        bgColor = ta.getInt(
            R.styleable.CircleDashboard_BackgroundColor,
            ContextCompat.getColor(context, R.color.base_light_gray)
        )
        borderWidth = ta.getFloat(R.styleable.CircleDashboard_BorderWidth,
            DisplayUtils.dpToPx(3f)
        )

        textPaint.textSize = DisplayUtils.sp2px(
            12f
        ).toFloat()
        textPaint.flags = Paint.ANTI_ALIAS_FLAG
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        maxPadding = paddingLeft.coerceAtLeast(paddingRight).coerceAtLeast(paddingTop)
            .coerceAtLeast(paddingBottom).toFloat()
        setPadding(maxPadding.toInt(), maxPadding.toInt(), maxPadding.toInt(), maxPadding.toInt())
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        viewSize = width.coerceAtMost(height * 2).coerceAtLeast(defaultViewSize.toInt()).toFloat()
        radius = (viewSize - maxPadding * 2 - borderWidth * 2) / 2f
        rectF.set(-radius, -radius, radius, radius)
        setMeasuredDimension(viewSize.toInt(), (viewSize / 2).toInt())
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.translate(width / 2f, height - maxPadding)
        canvas.save()

        bgPaint.strokeWidth = borderWidth.toFloat()
        bgPaint.color = bgColor
        bgPaint.isAntiAlias = true
        bgPaint.style = Paint.Style.STROKE
        canvas.drawArc(rectF, 180f, 180f, false, bgPaint)

        drawdials(canvas)
        drawPointer(canvas)
    }

    /**
     * 绘制刻度
     */
    private fun drawdials(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-180f)
        dialsPaint.strokeWidth = DisplayUtils.dpToPx(
            2f
        ).toFloat()
        dialsPaint.isAntiAlias = true
        dialsPaint.style = Paint.Style.STROKE

        bgPaint.color = progressColor
        canvas.drawArc(rectF, 0f, 180 * currentValue / maxValue, false, bgPaint)
        for (index in 0..100) {

            canvas.save()
            canvas.rotate(index * 1.8f)
            if (currentValue >= index) {
                dialsPaint.color = progressColor
                textPaint.color = progressColor

            } else {
                dialsPaint.color = bgColor
                textPaint.color = bgColor
            }
            if (index % 10 == 0) {

                canvas.drawLine(
                    radius,
                    0f,
                    radius - bigDialsLength,
                    0f,
                    dialsPaint
                )
                val dialsValue = "$index"
                val rect = Rect()
                textPaint.getTextBounds(dialsValue, 0, dialsValue.length, rect)

                canvas.drawText(
                    dialsValue,
                    radius - bigDialsLength - rect.width() - DisplayUtils.dpToPx(
                        2f
                    ),
                    when (index) {
                        0 -> rect.height() / 2f
                        100 -> 0f
                        else -> rect.height() / 3f
                    },
                    textPaint
                )
            } else
                canvas.drawLine(
                    radius,
                    0f,
                    (radius - smallDialsLength).toFloat(),
                    0f,
                    dialsPaint
                )
            canvas.restore()
        }
        canvas.restore()
    }

    /**
     * 绘件指针
     */
    private fun drawPointer(canvas: Canvas) {
        canvas.save()
        val centY = radius / 10
        val currentAngle = 180 * currentValue / maxValue
        canvas.rotate(180 + currentAngle)
        canvas.translate(centY, 0f)
        val path = Path()
        path.moveTo(-centY, 0f)
        path.lineTo(0f, (0.7 * centY).toFloat())
        path.lineTo(radius - bigDialsLength - (0.7 * centY).toFloat(), 0f)
        path.lineTo(0f, -(0.7 * centY).toFloat())
        path.moveTo(-(0.7 * centY).toFloat(), 0f)
        bgPaint.color = defaultProgressColor
        bgPaint.style = Paint.Style.FILL
        bgPaint.strokeWidth = 0f
        canvas.drawPath(path, bgPaint)
        bgPaint.color = ContextCompat.getColor(context, R.color.base_white)
        canvas.drawCircle(0f, 0f, (centY * 0.7 * 0.7 / 2).toFloat(), bgPaint)
        canvas.restore()
    }


    /**
     * 设置当前进度
     *
     */
    fun setProgress(progress: Int) {
        if (progress <= 0)
            currentValue = 0f
        if (progress >= 100)
            currentValue = 100f
        else
            currentValue = progress.toFloat()
        postInvalidate()
    }
}