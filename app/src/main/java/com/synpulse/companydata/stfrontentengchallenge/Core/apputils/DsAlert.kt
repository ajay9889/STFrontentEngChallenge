package com.synpulse.companydata.Core.apputils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.synpulse.companydata.stfrontentengchallenge.R

object DsAlert {

    fun onCreateDialog(ctx: Context): ProgressDialog {
        val dialog = ProgressDialog.show(ctx, null, null)
        dialog.setContentView(R.layout.loader)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        return dialog
    }
    fun showAlert(context: Activity,
                  title: String,
                  message: String,
                  positiveButton: String
    ): AlertDialog {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return  AlertDialog.Builder(context,android.R.style.ThemeOverlay_Material_Dialog_Alert)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .apply { positiveButton.let{ positiveString ->
                    setPositiveButton(positiveString) { dialog, which ->
                        dialog.cancel()
                    }
                }}.show()
        }else{
            return  AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .apply { positiveButton.let{ positiveString ->
                    setPositiveButton(positiveString) { dialog, which ->
                        dialog.cancel()
                    }
                }}.show()
        }

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