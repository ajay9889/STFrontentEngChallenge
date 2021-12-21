package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

sealed class ViewState<T>{
    data class Content<T>(val data:T) : ViewState<T>()
    class Loading<T>: ViewState<T>()
    data class Error<T>(val t: Throwable): ViewState<T>()
    class Empty<T>: ViewState<T>()
    data class Message<T>(val message: String): ViewState<T>()
    data class phoneAuthCredential<T>(val mPhoneAuthCredential: PhoneAuthCredential): ViewState<T>()
    data class verificationCodeToken<T>(val mPair: Pair<String, PhoneAuthProvider.ForceResendingToken>): ViewState<T>()
}