package de.fh_aachen.android.unit04.dialogs_demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DatePickerFragment.DatePickerResult, CustomDialogFragment.LoginResult {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Java-Style-Interface...
    fun doDialogDatePicker(v: View?) {
        DatePickerFragment().apply {
            setOnDatePickerResultListener(this@MainActivity)
            show(supportFragmentManager, "datePicker")
        }
    }

    override fun onDatePickerResult(canceled: Boolean, year: Int, month: Int, day: Int) {
        textView12.text = if (!canceled)"OK $day.$month.$year" else "Canceled"
    }

    fun doDialogAlert(v: View?) {
        AlertDialog.Builder(this).setMessage("Was nun?")
                .setTitle("Zucker oder Suessstoff?")
                .setCancelable(false)
                .setPositiveButton("Zucker") { _, id ->
                    val text = findViewById<TextView>(R.id.textView22)
                    text.text = "Zucker"
                }
                .setNegativeButton("Suessstoff") { _, id ->
                    val text = findViewById<TextView>(R.id.textView22)
                    text.text = "Suessstoff"
                }
                .setNeutralButton("Hmmm...") { _, id ->
                    val text = findViewById<TextView>(R.id.textView22)
                    text.text = "Beides"
                }           // Builder
                .create()   // Dialog
                .show()
    }

    // cf. DatePicker
    fun doDialogCustom(v: View?) {
        CustomDialogFragment().apply {
            isCancelable = false
            show(supportFragmentManager, "customDlg")
        }
    }

    override fun Login() {
        TextView32.text = "login"
    }

    override fun Logout() {
        TextView32.text = "logout"
    }
}
