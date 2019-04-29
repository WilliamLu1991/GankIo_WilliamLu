package com.williamlu.widgetlib.banner_card

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.williamlu.widgetlib.R


/**
 * @Author: WilliamLu
 * @Data: 2019/3/25
 * @Description:
 *
 *
 *
 */
class BannerIndicator : View {
    private var number: Int = 0
    private var position = 0
    private var paint = Paint()
    private var selectColor: Int = 0
    private var unSelectColor: Int = 0
    private var radius: Float = 0.toFloat()
    private var space: Float = 0.toFloat()
    val INDICATOR_STYLE_CIRCLE = 0
    val INDICATOR_STYLE_RECT = 1
    val INDICATOR_STYLE_OVAL = 2
    /*** 小点的样式，默认是圆形 */
    private var indicatorStyle = INDICATOR_STYLE_CIRCLE

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

        val typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.BannerIndicator)
        this.selectColor = typedArray.getColor(R.styleable.BannerIndicator_selectColor, Color.RED)
        this.unSelectColor = typedArray.getColor(R.styleable.BannerIndicator_unselectedColor, Color.GRAY)
        this.radius = typedArray.getDimension(R.styleable.BannerIndicator_radius, 10f)
        this.space = typedArray.getDimension(R.styleable.BannerIndicator_space, 20f)
        this.indicatorStyle = typedArray.getInt(R.styleable.BannerIndicator_indicatorStyle, INDICATOR_STYLE_CIRCLE)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.setStyle(Paint.Style.FILL)
        paint.setAntiAlias(true)
        //paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        var startPosition = width / 2 - (radius * 2 * number + space * (number - 1)) / 2

        canvas.save()
        for (i in 0 until number) {
            if (i == position) {
                paint.color = selectColor
            } else {
                paint.color = unSelectColor
            }

            //canvas.drawCircle(startPosition + radius * (2 * i + 1) + i * space, (height / 2).toFloat(), radius, paint)

            var left = (startPosition + i * radius * 2 + space * i).toInt()

            if (indicatorStyle === INDICATOR_STYLE_CIRCLE) {
                canvas.drawCircle(left + radius , (height / 2).toFloat(), radius, paint)
            } else if (indicatorStyle === INDICATOR_STYLE_RECT) {
                var rect = Rect()
                rect.left = left
                rect.top = paddingTop
                rect.right = (left + radius * 4).toInt()
                rect.bottom = (rect.top + radius * 2).toInt()
                canvas.drawRect(rect, paint)
            }else if (indicatorStyle === INDICATOR_STYLE_OVAL) {
                var rectF = RectF()
                rectF.left = left.toFloat()
                rectF.top = paddingTop.toFloat()
                rectF.right = left + radius * 4
                rectF.bottom = rectF.top + radius * 2
                canvas.drawRoundRect(rectF,15F,15F, paint)
            }

        }
        canvas.restore()

    }

    fun setNumber(number: Int) {
        this.number = number
        invalidate()
    }

    fun setPosition(position: Int) {
        this.position = position
        invalidate()
    }


}