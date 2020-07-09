package de.fh_aachen.android.unit03.upper_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val KEY = "myresult"
private const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun makeUpper(view: View?) {
        // query all matching receivers
        val intent = Intent(Intent.ACTION_SEND).apply { type = "text/plain" }
        packageManager.queryIntentActivities(intent,0).forEach {
            Log.i("MainActivity","activity $it")
        }

        startActivityForResult(
                // via dialog, note: receiving app must be known
                Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, editText.text.toString())
                        }
                        , "Receiver?"),
                REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                upperText.text = data?.extras?.get(KEY)?.toString() ?: "-"
            }
        }
    }

}
