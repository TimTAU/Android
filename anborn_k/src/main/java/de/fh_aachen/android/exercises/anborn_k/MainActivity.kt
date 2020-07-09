package de.fh_aachen.android.exercises.anborn_k

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickButton(v: View?) {
        Toast.makeText(this, "Pressed!", Toast.LENGTH_LONG).show()
    }

}
