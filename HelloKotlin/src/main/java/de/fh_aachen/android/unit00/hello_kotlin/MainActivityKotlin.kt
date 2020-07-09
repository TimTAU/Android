package de.fh_aachen.android.unit00.hello_kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "Hello_Kotlin"

class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // convert stages...
        // 1. from Java
        // findViewById<View>(R.id.button).setOnClickListener { v: View? -> onClickButton(v) }
        // 2. Kotlin-style
        // button.setOnClickListener { _: View? -> onClickButton() }
        // 3. short form
        // button.setOnClickListener { onClickButton() }
        // 4. finally, without additional methods
        button.setOnClickListener {
            Toast.makeText(this, "Pressed!", Toast.LENGTH_LONG).show()
            Log.i(TAG, "onClickButton - button pressed!")
        }
    }

//    private fun onClickButton() {
//        Toast.makeText(this, "Pressed!", Toast.LENGTH_LONG).show()
//        Log.i(TAG, "onClickButton - button pressed!")
//    }

}
