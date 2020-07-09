package de.fh_aachen.android.unit03.activity_demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG,"First.onCreate, ref $this")
        if (savedInstanceState != null) {
            val key = resources.getString(R.string.save_key)
            val value = savedInstanceState.getString(key)
            Log.i(TAG,"First.onCreate: value='$value'")
        }
    }

    fun startSecondActivity(view: View) {
//        val key = resources.getString(R.string.transfer_key)
//        val ed = findViewById<View>(R.id.editText) as EditText
//        val value = ed.text.toString()
//        val intent = Intent(this, SecondActivity::class.java)
//        /* long way
//        Bundle extras = new Bundle();
//        extras.putString(key, value);
//        intent.putExtras(extras);
//         */
//        intent.putExtra(key, editText.text.toString()) // short way
//        startActivity(intent)
        startActivity(
                Intent(this, SecondActivity::class.java).apply {
                    putExtras(Bundle().apply {
                        putString(resources.getString(R.string.transfer_key), editText.text.toString())
                    })
                }
        )
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"First.onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"First.onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG,"First.onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG,"First.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"First.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"First.onDestroy, ref $this")
    }

    // after onPause
    override fun onSaveInstanceState(savedInstanceState: Bundle) { // Save something
        val key = resources.getString(R.string.save_key)
        savedInstanceState.putString(key, "something")
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"First.onSaveInstanceState")
    }

    // is called after onStart(), whereas onCreate() is called before onStart()
    // called only when recreating activity after it was killed
    // same bundle as in onCreate
    override fun onRestoreInstanceState(savedInstanceState: Bundle) { // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG,"First.onRestoreInstanceState")
        // restore something
        val key = resources.getString(R.string.save_key)
        val value = savedInstanceState.getString(key)
        Log.i(TAG,"First.onRestoreInstanceState: value='$value'")
    }

}
