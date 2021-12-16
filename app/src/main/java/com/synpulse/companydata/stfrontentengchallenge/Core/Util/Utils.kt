package com.synpulse.companydata.stfrontentengchallenge.Core.Util

object Utils {
    fun isValidEmail(inComingEmail:String): Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
       return inComingEmail.matches(emailPattern.toRegex())
    }
}