package com.synpulse.companydata.Core.apputils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Activity.MainActivity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
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

    fun showToastMessage(context: Context , message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }



    fun showAlertLogout(activity: Activity,
                        userSignInViewModel: UserSignInViewModel,
                        databasehelper: Databasehelper,
                        title: String,
                        message: String,
                        positiveButton: String): AlertDialog {
        return  AlertDialog.Builder(activity,android.R.style.ThemeOverlay_Material_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .apply { positiveButton.let { positiveString ->
                setPositiveButton(positiveString) { dialog, which ->
                    dialog.cancel()
                    userSignInViewModel.onSignOut().apply {
                        // clearing all data
                        databasehelper.RoomDataAccessObejct().clearComapnyData()
                        databasehelper.RoomDataAccessObejct().clearTbGlobalQouteComapnyData().apply {
                            activity.startActivity(Intent(activity, MainActivity::class.java))
                            activity.finish()
                        }
                    }
                }
                setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }
            }
            }.show()
    }
}