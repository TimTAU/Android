package de.fh_aachen.android.unit02.orientation_demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isPortrait = findViewById<View?>(R.id.textViewP) != null
        Toast.makeText(this,"onCreate() - Portrait? $isPortrait",Toast.LENGTH_SHORT).show()
    }

}
