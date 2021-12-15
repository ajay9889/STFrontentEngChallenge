package com.synpulse.companydata.Core.apputils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
object DsAlert {
    @RequiresApi(Build.VERSION_CODES.M)
    fun showAlert(context: Activity,
                  title: String,
                  message: String,
                  positiveButton: String
    ): AlertDialog {
        return  AlertDialog.Builder(context,android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .apply { positiveButton.let{ positiveString ->
                setPositiveButton(positiveString) { dialog, which ->
                    dialog.cancel()
                }
            }}.show()
    }

    fun showAlertFinish(context: Activity,
                        title: String,
                        message: String,
                        positiveButton: String
    ): AlertDialog {
        return  AlertDialog.Builder(context,android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .apply { positiveButton.let { positiveString ->
                setPositiveButton(positiveString) { dialog, which ->
                    context.finish()
                    dialog.cancel()
                }
            }
            }.show()
    }
}