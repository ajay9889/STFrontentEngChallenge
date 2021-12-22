package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthProvider
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Activity.HomeActivity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.OtpfragmentBinding
import org.koin.android.ext.android.inject

class OtpFragment : BaseFragment<OtpfragmentBinding>(OtpfragmentBinding::inflate) {
    val mobileOTPViewModel: UserSignInViewModel by inject()
    lateinit var enteredMobileNumber: String ;
    var tokenPAP: PhoneAuthProvider.ForceResendingToken?=null;
    var verificationPhoneId: String?=null;
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
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
        (arguments?.getParcelable<PhoneAuthProvider.ForceResendingToken>("tokenPAP"))?.let{
            mobileOTPViewModel.tokenPAP = it
        }
        arguments?.getString("verificationPhoneId")?.let{
            mobileOTPViewModel.verificationPhoneId = it
        }
        Log.d("VERIFICATION _1" ,""+tokenPAP);
        Log.d("VERIFICATION _2" ,""+arguments);
        Log.d("VERIFICATION _3",""+verificationPhoneId);
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.cancel()
        with(viewBinding){
            resendOTP.setOnClickListener {
                mobileOTPViewModel.tokenPAP?.let {
                    mobileOTPViewModel.onResendForOTP(requireActivity(), enteredMobileNumber, it);
                }
            }
            btnSubmit.setOnClickListener {
                mobileOTPViewModel.verificationPhoneId?.let {
                    if(validateField())
                        mobileOTPViewModel.verifyEnteredCode(requireActivity(), it ,verifyOTP.text.toString() );
                }
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
        mobileOTPViewModel.registeredUserInfo.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Loading -> {
                    dialog?.show()
                }
                is ViewState.phoneAuthCredential -> {
                    dialog?.cancel()
                    mobileOTPViewModel.signInWithPhoneAuthCredential(requireActivity(), it.mPhoneAuthCredential);
                }
                is ViewState.verificationCodeToken -> {
                    dialog?.cancel()
                }
                is ViewState.Content -> {
                    dialog?.cancel()
                    requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                    requireActivity().finish()
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message.let { msg ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            msg,"Okay")
                    }
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