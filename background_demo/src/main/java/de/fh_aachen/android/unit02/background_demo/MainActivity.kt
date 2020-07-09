package de.fh_aachen.android.unit02.background_demo

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.util.*

class MainActivity : AppCompatActivity() {

    var rnd = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBoxIsRunning.isChecked = false
    }

    fun startScene1(view: View?) {
        for (i in 0..9) {
            checkBoxIsRunning.isChecked = true
            colorBox()
            sleep500()
            checkBoxIsRunning.isChecked = false
        }
    }

    // one version, via Threads
    fun startScene2(view: View?) {
        Thread(Runnable {
            checkBoxIsRunning.post { checkBoxIsRunning.isChecked = true }
            for (i in 0..9) {
                runOnUiThread { colorBox() }
                sleep500()
            }
            checkBoxIsRunning.post { checkBoxIsRunning.isChecked = false }
        }).start()
    }

    // another version, via AsyncTask
    fun startScene3(view: View?) {
        Scene(this).execute("one","two")
    }

    class Scene(context: MainActivity) : AsyncTask<String?, Int?, Boolean>() {

        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            activityReference.get()?.checkBoxIsRunning?.isChecked = true
        }

        override fun onProgressUpdate(vararg values: Int?) {
            activityReference.get()?.colorBox()
        }

        override fun onPostExecute(result: Boolean) {
            activityReference.get()?.checkBoxIsRunning?.isChecked = false
        }

        override fun doInBackground(vararg params: String?): Boolean {
            for (i in 0..9) {
                publishProgress(23,42)
                activityReference.get()?.sleep500()
            }
            return true
        }

    }

    private fun colorBox() {
        frameLayoutColorBox.setBackgroundColor(
                Color.argb(0xff, 255, rnd.nextInt(255), 128))
    }

    private fun sleep500() {
        try {
            Thread.sleep(500, 0)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


}
