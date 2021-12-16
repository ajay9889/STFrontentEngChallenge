package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthProvider
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.OtpfragmentBinding
import com.synpulse.companydata.stfrontentengchallenge.databinding.RegisterBinding
import org.koin.android.ext.android.inject

class OtpFragment : BaseFragment<OtpfragmentBinding>(OtpfragmentBinding::inflate) {
    val userSignInViewModel: UserSignInViewModel by inject()
     var token: PhoneAuthProvider.ForceResendingToken?=null;
    lateinit var enteredMobileNumber: String ;
    var dialog: ProgressDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observData()
        mCountDownTimer.start()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.cancel()
    }
    fun setUp(){
        enteredMobileNumber= arguments?.getString("mobilenumber").toString()
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.cancel()
        with(viewBinding){
            resendOTP.setOnClickListener {
                token?.let {
                    userSignInViewModel.onResendForOTP(requireActivity(), enteredMobileNumber, it);
                }
            }
            btnSubmit.setOnClickListener {
                if(validateField())
                    userSignInViewModel.verifyEnteredCode(requireActivity(), enteredMobileNumber, verifyOTP.text.toString() );
            }
        }
    }

    var mCountDownTimer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            with(viewBinding){
                resendOTP.setText(java.lang.String.format(requireContext().getResources().getString(R.string.resend_otp_code), millisUntilFinished / 1000))
            }
        }
        override fun onFinish() {
            with(viewBinding){
                resendOTP.setText(requireContext().getResources().getString(R.string.resendotp))
                resendOTP.setPaintFlags(resendOTP.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
            }
        }
    }
    fun validateField(): Boolean{
        with(viewBinding){
            if(verifyOTP.text.toString().isNullOrBlank())
            {
                DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                    "Enter received pin","Okay")
                return false
            }
        }
        return true
    }

    fun observData(){
        userSignInViewModel.registeredUserInfo.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Loading -> {
                    dialog?.show()
                }
                is ViewState.phoneAuthCredential -> {
                    dialog?.cancel()
                    userSignInViewModel.signInWithPhoneAuthCredential(requireActivity(), it.mPhoneAuthCredential);
                }
                is ViewState.verificationCodeToken -> {
                    dialog?.cancel()

                }
                is ViewState.Content -> {
                    dialog?.cancel()
                    findNavController().navigate(R.id.action_signinFrgmnt_to_homeFragment)
                }
                is ViewState.Error -> {
                    dialog?.cancel()
                    it.t.message?.let { it1 ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            it1,"Okay")
                    }
                }
            }
        })
    }
}