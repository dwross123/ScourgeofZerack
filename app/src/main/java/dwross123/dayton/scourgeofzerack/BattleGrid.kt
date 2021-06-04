package dwross123.dayton.scourgeofzerack


import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View


class BattleGrid(context: Context, attrs: AttributeSet) : View(context, attrs){
    /*fun isShowText(): Boolean {
        return mShowText
    }

    fun setShowText(showText: Boolean) {
        mShowText = showText
        invalidate()
        requestLayout()
    }
    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChart,
            0, 0).apply {

            try {
                mShowText = getBoolean(R.styleable.PieChart_showText, false)
                textPos = getInteger(R.styleable.PieChart_labelPosition, 0)
            } finally {
                recycle()
            }
        }
    }
    val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = textColor
/*            if (textHeight == 0f) {
                textHeight = textSize
            } else {
                textSize = textHeight
            }*/
    }*/

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BattleGrid,
            0, 0).apply {

            /*try {
                //mShowText = getBoolean(R.styleable.PieChart_showText, false)
                //textPos = getInteger(R.styleable.PieChart_labelPosition, 0)
            } finally {
                recycle()
            }*/
        }
    }
    val battleGridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        //textSize = textHeight
    }

    val shadowPaint = Paint(0).apply {
        color = 0x101010
        maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)

        // Account for padding
        var xpad = (paddingLeft + paddingRight).toFloat()
        val ypad = (paddingTop + paddingBottom).toFloat()

        // Account for the label
        //if (showText) xpad += textWidth

        val ww = width.toFloat() - xpad
        val hh = height.toFloat() - ypad

        // Figure out how big we can make the pie.
        val diameter = Math.min(ww, hh)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try for a width based on our minimum
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        val minh: Int = View.MeasureSpec.getSize(w) + paddingBottom + paddingTop
        val h: Int = View.resolveSizeAndState(
            View.MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )

        setMeasuredDimension(w, h)
    }

    /*override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            // Draw the shadow
            drawOval(shadowBounds, shadowPaint)

            // Draw the label text
            //drawText(data[mCurrentItem].mLabel, textX, textY, textPaint)

            // Draw the pie slices
            data.forEach {
                battleGridPaint.shader = it.mShader
                drawArc(bounds,
                    360 - it.endAngle,
                    it.endAngle - it.startAngle,
                    true, battleGridPaint)
            }

            // Draw the pointer
            //drawLine(textX, pointerY, pointerX, pointerY, textPaint)
            //drawCircle(pointerX, pointerY, pointerSize, mTextPaint)
        }
    }*/
}