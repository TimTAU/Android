package de.fh_aachen.android.unit06.customview_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

class StrokeView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private var strokeColor: Int

    init {
        // hole die Extra Attribute, see attrs.xml
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StrokeView, 0, 0)
        val preText = attributes.getString(R.styleable.StrokeView_preText) ?: ""
        val postText = attributes.getString(R.styleable.StrokeView_postText) ?: ""
        strokeColor = attributes.getColor(R.styleable.StrokeView_strokeColor, ContextCompat.getColor(context, R.color.colorStrokeDefault))
        attributes.recycle() // Object/Speicher wird freigegeben

        text = "$preText ${text} $postText"
    }

    // wie gross ist das neue View?
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // es kann kompliziert sein, rufe zunächst das Original auf
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // hier stehen die berechneten Groessen
        val width = measuredWidth
        val height = measuredHeight

        // erhöhe die Höhe um 1/3, aber ohne Padding
        val heigthWithoutPadding = height - paddingTop - paddingBottom
        val addSizey = heigthWithoutPadding / 3

        // so werden die neuen Grössen gesetzt
        setMeasuredDimension(width, heigthWithoutPadding + addSizey + paddingTop + paddingBottom)
    }

    // hier wird das View "gemalt"
    override fun onDraw(canvas: Canvas) {
        // zuerst das Originalview (der Text)
        super.onDraw(canvas)
//        // dann die Striche
        canvas.apply {
            val paint = Paint().apply {
                style = Paint.Style.STROKE
                color = strokeColor
                strokeWidth = 4f
            }
            drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)
            drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint)
        }
    }

}
