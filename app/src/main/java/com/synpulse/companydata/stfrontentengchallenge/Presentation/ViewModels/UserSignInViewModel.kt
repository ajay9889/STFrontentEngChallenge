package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.synpulse.companydata.stfrontentengchallenge.MainApplication
import java.lang.Appendable
import java.util.concurrent.TimeUnit

class UserSignInViewModel (val application: MainApplication): AndroidViewModel(application){
    private var TAG= "FirebaseAuth"
    public val registeredUserInfo =MutableLiveData<ViewState<FirebaseUser>>()
    private var authFb: FirebaseAuth
    init {
        authFb = Firebase.auth
    }
    fun onSignOut(){
        authFb.signOut()
    }
    fun onSignup(activity: Activity, userName: String, password: String){
        registeredUserInfo.postValue(ViewState.Loading())
        authFb.createUserWithEmailAndPassword(userName, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    authFb.currentUser?.let {
                        registeredUserInfo.postValue(ViewState.Content(it))
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "failure", task.exception)
                    registeredUserInfo.postValue(task.exception?.let { it1 ->
                        ViewState.Error(
                            it1.fillInStackTrace())
                    })
                }
            }
    }


    fun onSignIn(activity: Activity, userName: String, password: String){
        authFb.signInWithEmailAndPassword(userName, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    authFb.currentUser?.let {
                        registeredUserInfo.postValue(ViewState.Content(it))
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "failure", task.exception)
                    registeredUserInfo.postValue(task.exception?.let { it1 ->
                        ViewState.Error(
                            it1.fillInStackTrace())
                    })
                }
            }
    }

    fun onResendForOTP(activity: Activity , phoneNumber: String, token:PhoneAuthProvider.ForceResendingToken){
        val options = PhoneAuthOptions.newBuilder(authFb)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setForceResendingToken(token)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted:$credential")
                    registeredUserInfo.postValue(ViewState.phoneAuthCredential(credential))
                }
                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e)
                    if (e is FirebaseAuthInvalidCredentialsException) {
                        registeredUserInfo.postValue(ViewState.Message("Invalid request"))
                    } else if (e is FirebaseTooManyRequestsException) {
                        registeredUserInfo.postValue(ViewState.Message("The SMS quota for the project has been exceeded"))
                    }
                }
                override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken) {
                    Log.d(TAG, "onCodeSent:$verificationId")
                    registeredUserInfo.postValue(ViewState.verificationCodeToken(Pair(verificationId, token)))
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun onPhoneNumberVerificationsIn(activity: Activity , phoneNumber: String){
        val options = PhoneAuthOptions.newBuilder(authFb)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted:$credential")
                    registeredUserInfo.postValue(ViewState.phoneAuthCredential(credential))

                }
                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e)
                    if (e is FirebaseAuthInvalidCredentialsException) {
                        registeredUserInfo.postValue(ViewState.Message("Invalid request"))
                    } else if (e is FirebaseTooManyRequestsException) {
                        registeredUserInfo.postValue(ViewState.Message("The SMS quota for the project has been exceeded"))
                    }
                }
                override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken) {
                    Log.d(TAG, "onCodeSent:$verificationId")
                    registeredUserInfo.postValue(ViewState.verificationCodeToken(Pair(verificationId, token)))
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }



    fun verifyEnteredCode(activity: Activity ,verificationId: String, code: String){
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(activity ,credential)
    }

     fun signInWithPhoneAuthCredential(activity: Activity, credential: PhoneAuthCredential) {
        authFb.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    authFb.currentUser?.let {
                        registeredUserInfo.postValue(ViewState.Content(it))
                    }
                } else {
                    registeredUserInfo.postValue(task.exception?.let { it1 ->
                        ViewState.Error(
                            it1.fillInStackTrace())
                    })
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }


}