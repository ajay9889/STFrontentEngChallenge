package com.synpulse.companydata.Core.networkutils

import android.content.Context
import android.net.ConnectivityManager

object NetworkConnectivity {
    fun isNetworkConnected(contest: Context)= (contest.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).let {
        it.activeNetwork!=null && it.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}