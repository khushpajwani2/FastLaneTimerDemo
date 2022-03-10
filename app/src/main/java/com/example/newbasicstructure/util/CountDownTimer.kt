package com.example.newbasicstructure.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Khush Pajwani.
 * Date 3/9/2022
 */
@SuppressLint("ViewConstructor")
class CountDownTimer : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        paint.strokeWidth = 3F
        canvas.drawRect(30F, 30F, 80F, 80F, paint)
        paint.strokeWidth = 0F
        paint.color = Color.CYAN
        canvas.drawRect(33F, 60F, 77F, 77F, paint)
        paint.color = Color.YELLOW
        canvas.drawRect(33F, 33F, 77F, 60F, paint)
    }
}