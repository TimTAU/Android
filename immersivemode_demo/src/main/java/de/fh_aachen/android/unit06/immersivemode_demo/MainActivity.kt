package de.fh_aachen.android.unit06.immersivemode_demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

// adapted from https://developer.android.com/samples/ui.html

// note changes in styles!

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {  toggleView() }
    }

    private fun toggleView() {
        window?.apply {
            var newUiOptions = decorView.systemUiVisibility

            // current state
            // isImmersiveModeEnabled = newUiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions

            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
            newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

            decorView.systemUiVisibility = newUiOptions
        }

        // cf. xor see kotlin infix notation
        val a = Operand()
        val b = Operand()
        val x = a + b
        val y = a cross b
    }

    // https://kotlinlang.org/docs/reference/operator-overloading.html
    class Operand {
        operator fun plus(x: Operand) = Operand()
        infix fun cross(x: Operand) = Operand()
    }

}

