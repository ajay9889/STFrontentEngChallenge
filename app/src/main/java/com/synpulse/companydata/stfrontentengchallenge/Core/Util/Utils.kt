package com.synpulse.companydata.stfrontentengchallenge.Core.Util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synpulse.companydata.Core.apputils.GridSpacingItemDecoration
import androidx.core.content.ContextCompat.getSystemService




object Utils {
    fun isValidEmail(inComingEmail:String): Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
       return inComingEmail.matches(emailPattern.toRegex())
    }

    fun hideKeyboard(context: Context , view: View){
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.getWindowToken() ,0 )
    }
    fun itemGridListDecore(context: Context ,recyclerView: RecyclerView){
        recyclerView.setHasFixedSize(true)
        val spanCount = 5 // 3 columns
        val spacing = 5 // 50px
        val includeEdge = false
        val widths = context.resources.displayMetrics.widthPixels.toFloat()
        val heightPixels = context.resources.displayMetrics.heightPixels.toFloat()
        val width = widths.toInt()
        var gridLayoutManager: GridLayoutManager? = null

        gridLayoutManager = if (widths > heightPixels) {
            GridLayoutManager(context, 5)
        } else {
            if (width < 700) GridLayoutManager(
                context,
                2
            ) else GridLayoutManager(context, 3)
        }
        recyclerView.setLayoutManager(gridLayoutManager)
        val itemDecoration = GridSpacingItemDecoration(spanCount, spacing, includeEdge)
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setOnFlingListener(null);
    }
}