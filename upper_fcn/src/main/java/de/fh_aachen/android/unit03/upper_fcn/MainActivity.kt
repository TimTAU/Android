package de.fh_aachen.android.unit03.upper_fcn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val KEY = "myresult"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // type and filter must match
        intent.apply {
            if (type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let {
                    putExtra(KEY, it.toUpperCase())
                    setResult(Activity.RESULT_OK, this)
                }
            }
        }
        finish() // end without live-cycle
    }

}
