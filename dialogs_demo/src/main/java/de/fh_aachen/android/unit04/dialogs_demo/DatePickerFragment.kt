package de.fh_aachen.android.unit04.dialogs_demo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), OnDateSetListener {

    interface DatePickerResult {
        fun onDatePickerResult(canceled: Boolean, year: Int, month: Int, day: Int)
    }

    var cancel = false
    var year = 0
    var month = 0
    var day = 0
    var datePickerResult: DatePickerResult? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        year = c[Calendar.YEAR]
        month = c[Calendar.MONTH]
        day = c[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(this.context!!, this, year, month, day)
    }

    fun setOnDatePickerResultListener(call: DatePickerResult?) {
        datePickerResult = call
    }

    override fun onCancel(dialog: DialogInterface) {
        Toast.makeText(activity, "canceled", Toast.LENGTH_SHORT).show()
        cancel = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        Toast.makeText(activity, "dismissed, cancel $cancel day $day", Toast.LENGTH_SHORT).show()
        if (datePickerResult != null) datePickerResult!!.onDatePickerResult(cancel, year, month, day)
    }

    override fun onDateSet(view: DatePicker, y: Int, m: Int, d: Int) {
        year = y
        month = m
        day = d
        Toast.makeText(activity, "set day$day", Toast.LENGTH_SHORT).show()
    }
}
