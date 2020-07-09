package de.fh_aachen.android.unit07.dragndrop_demo

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.View.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dragListener: MyDragEventListener
    private lateinit var longListener: MyOnLongClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dragListener = MyDragEventListener()
        longListener = MyOnLongClickListener()

        // set long click listener
        for (id in intArrayOf(R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.textView1)) {
            findViewById<View>(id).setOnLongClickListener(longListener)
        }

        // set drag listener
        for (id in intArrayOf(R.id.imageViewTarget1, R.id.imageViewTarget2)) {
            findViewById<View>(id).setOnDragListener(dragListener)
        }
    }

    private fun showInfo(info: String) {
        textAction.text = getString(R.string.drag_action) + info
    }

    inner class MyOnLongClickListener : OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            val tag = view.tag.toString()
            val dragData = ClipData.newPlainText("text", tag)
            view.startDragAndDrop(dragData,DragShadowBuilder(view),null, 0)
            return true
        }
    }

    inner class MyDragEventListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            var rc = true
            when (event.action) {
                // determines if this View can accept the dragged data
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                        showInfo("drag started")
                    else
                        rc = false
                }
                DragEvent.ACTION_DRAG_ENTERED -> { showInfo("drag entered") }
                DragEvent.ACTION_DRAG_LOCATION -> { showInfo("drag location x:" + event.x + " y:" + event.y) }
                DragEvent.ACTION_DRAG_EXITED -> { showInfo("drag exited") }
                DragEvent.ACTION_DROP -> {
                    val dragData = event.clipData.getItemAt(0).text.toString()
                    val tag = v.tag.toString()
                    Toast.makeText(applicationContext, "Src: $dragData Dst: $tag", Toast.LENGTH_LONG).show()
                }
                DragEvent.ACTION_DRAG_ENDED -> { showInfo("drag ended, ${if (event.result) "ok" else "nok" }") }
                else -> { rc = false }
            }
            return rc
        }
    }
}
