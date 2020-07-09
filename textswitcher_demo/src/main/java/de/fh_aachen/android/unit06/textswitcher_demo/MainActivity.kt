package de.fh_aachen.android.unit06.textswitcher_demo

import android.os.Bundle
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

// adapted from https://developer.android.com/samples/ui.html

class MainActivity : AppCompatActivity() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the factory used to create TextViews to switch between.
        switcher.apply {
            setFactory {
                TextView(this@MainActivity).apply {
                    gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                    setTextAppearance(android.R.style.TextAppearance_Large)
                }
            }
            inAnimation = AnimationUtils.loadAnimation(this@MainActivity, android.R.anim.fade_in)
            outAnimation = AnimationUtils.loadAnimation(this@MainActivity, android.R.anim.fade_out)
            setCurrentText(counter.toString())
        }

        button.setOnClickListener {
            counter++
            switcher.setText(counter.toString())
        }
    }

}
