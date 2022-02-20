package com.my.skyalbum.view.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils.getMeasuredHeight
import com.blankj.utilcode.util.SizeUtils.getMeasuredWidth

class ChatIconDrawable(private val view: ImageView, private val text: String) : Drawable() {
    private val paint: Paint = Paint()
    override fun draw(canvas: Canvas) {
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length - 1, bounds)
        val fontMetrics = paint.fontMetrics
        val baseline = (getMeasuredHeight(view) - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawText(text, (getMeasuredWidth(view)/2 - bounds.width()/2).toFloat(), baseline, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.setAlpha(alpha)
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.setColorFilter(cf)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    init {
        paint.setColor(Color.WHITE)
        paint.setTextSize(40f)
        paint.strokeWidth = 3f
        paint.setAntiAlias(true)
        paint.setFakeBoldText(true)
//        paint.setShadowLayer(6f, 0f, 0f, Color.BLACK)
        paint.setStyle(Paint.Style.FILL)
        paint.setTextAlign(Paint.Align.CENTER)
    }
}