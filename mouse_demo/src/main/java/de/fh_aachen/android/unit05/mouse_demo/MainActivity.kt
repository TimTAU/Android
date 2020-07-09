package de.fh_aachen.android.unit05.mouse_demo

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //var mVelocityTracker: VelocityTracker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // index vs. id:
        // pointer index of a pointer may change, but its id will remain the same, e.g.
        // while making gestures if you touch the screen with a second finger and remove
        // the first one then the index will change but the id (second finger) remains the same

        val index = event.actionIndex
        val action = event.actionMasked // action without pointer info
        val id = event.getPointerId(index)

        // pair destructing; also for Triple, e.g.: val (a,b) = Pair(1,2)
        val (mouse,consumed) = when (action) {
            MotionEvent.ACTION_DOWN -> Pair("down",true)
            MotionEvent.ACTION_MOVE -> Pair("move",true)
            MotionEvent.ACTION_UP -> Pair("up",true)
            MotionEvent.ACTION_CANCEL -> Pair("cancel",true)
            else -> Pair("?",false) // return super.onTouchEvent(event)
        }

        textView0.text = "idx:$index action:$action id:$id"
        textView1.text = "$mouse x:${event.rawX} y:${event.rawY}"

        return if (consumed) true else super.onTouchEvent(event)
    }
}
