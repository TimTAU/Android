package de.fh_aachen.android.unit04.dialogs_demo

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class CustomDialogFragment : DialogFragment() {

    interface LoginResult {
        fun Login()
        fun Logout()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate and set the layout for the dialog
        return AlertDialog.Builder(activity).setView(activity?.layoutInflater?.inflate(R.layout.dialog_signin, null))
            // Add action buttons, assumes it implements Interface
                .setPositiveButton("log in") { _, _ -> (activity as LoginResult).Login() }
                .setNegativeButton("log out") { _, _ -> (activity as LoginResult).Logout() }
                .create()
    }

}
