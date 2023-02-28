package com.adlemgames.pbk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.text.AttributedCharacterIterator.Attribute

class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                           defStyleAttr: Int = 0) : // что-то сложное со стэковерфлоу
    View(context, attrs, defStyleAttr){
    var startX: Float = 0f
    var startY: Float = 0f

    val paint = Paint()
    val lines = mutableListOf<MutableList<Float>>()
    var preview = mutableListOf(0f, 0f, 0f, 0f)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lines.clear()
        //this.setOnTouchListener { v, event ->
        //    if (event.action == MotionEvent.ACTION_DOWN){
        //        startX = event.x
        //        startY = event.y
        //    }
        //    if (event.action == MotionEvent.ACTION_MOVE){
        //        preview = mutableListOf(startX, startY, event.x, event.y)
        //        this.invalidate()
        //    }
        //    else if (event.action == MotionEvent.ACTION_UP){
        //        draw(startX, startY, event.x, event.y)
        //        preview = mutableListOf(0f, 0f, 0f, 0f)
        //    }
        //    performClick()
        //    true
        //}
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.strokeWidth = 5f
        paint.color = Color.parseColor("#565AFF")
        canvas?.drawLine(preview[0], preview[1], preview[2], preview[3], paint)
        for (line in lines) canvas?.drawLine(line[0], line[1], line[2], line[3], paint)
    }
    fun draw(startX: Float, startY: Float, stopX: Float, stopY: Float){
        lines.add(mutableListOf(startX, startY, stopX, stopY))
        this.invalidate()
    }
}