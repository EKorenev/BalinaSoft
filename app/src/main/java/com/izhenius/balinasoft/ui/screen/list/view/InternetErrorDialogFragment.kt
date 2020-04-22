package com.izhenius.balinasoft.ui.screen.list.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class InternetErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            dialogBuilder
                .setTitle("SERVER ERROR:")
                .setMessage("Please, check your internet connection!")
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ -> dialog.cancel() }
                .create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}