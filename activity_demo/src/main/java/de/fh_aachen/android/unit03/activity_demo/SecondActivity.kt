package de.fh_aachen.android.unit03.activity_demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

private const val TAG = "SecondActivity"

class SecondActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.i(TAG,"Second.onCreate, ref $this")
        textViewFromFirst.text = intent.extras?.getString(resources.getString(R.string.transfer_key)) ?: ""
    }

    fun startFirstActivity(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
