package de.fh_aachen.android.unit05.gesture_demo

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*

// see also: https://developer.android.com/guide/input

// there is also a simpler version SimpleOnGestureListener
class MainActivity : AppCompatActivity(),
        GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener,
        OnDoubleTapListener {

    private lateinit var gestureDetector: GestureDetectorCompat
    private lateinit var scaleDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set context and listeners
        gestureDetector = GestureDetectorCompat(this, this).apply { setOnDoubleTapListener(this@MainActivity) }
        scaleDetector = ScaleGestureDetector(this, this)
    }

    // do not forget!!!
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //return scaleDetector.onTouchEvent(event) ||
        return gestureDetector.onTouchEvent(event)
                || super.onTouchEvent(event)
    }

    override fun onScale(detector: ScaleGestureDetector?) = true

    override fun onScaleBegin(detector: ScaleGestureDetector?) = true

    override fun onScaleEnd(detector: ScaleGestureDetector?) {}

    // only return false if you want to ignore further interfaces such as SimpleOnGestureListener
    override fun onDown(event: MotionEvent): Boolean {
        textView1.text = "down"
        return true
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent,
                         velocityX: Float, velocityY: Float): Boolean {
        textView1.text = "fling"
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        textView1.text = "long press"
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float,
                          distanceY: Float): Boolean {
        textView1.text = "onscroll"
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        textView1.text = "showpress"
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        textView1.text = "singletapup"
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        textView1.text = "doubletap"
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        textView1.text = "doubletapevent"
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        textView1.text = "singletapconfirmed"
        return true
    }
}
