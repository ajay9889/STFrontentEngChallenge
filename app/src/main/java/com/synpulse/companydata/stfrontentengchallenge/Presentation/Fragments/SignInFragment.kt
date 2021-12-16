package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.SigninBinding
import org.koin.android.ext.android.inject


class SignInFragment : BaseFragment<SigninBinding>(SigninBinding::inflate) {
    val userSignInViewModel: UserSignInViewModel by inject()
    var dialog: ProgressDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.cancel()
    }
    fun setUp(){
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.cancel()
        with(viewBinding){
            buttonSignIN.setOnClickListener {
                if(validateField())
                    userSignInViewModel.onPhoneNumberVerificationsIn(requireActivity(), editTextPhone.text.toString() );
            }
        }
    }
    fun validateField(): Boolean{
        with(viewBinding){
            editTextPhone.setError(null)
            if(editTextPhone.text.toString().isNullOrBlank())
            {
                editTextPhone.setError("Enter Mobile Number")
                editTextPhone.isFocusable =true
                return false
            }else if(PhoneNumberUtils.isGlobalPhoneNumber(editTextPhone.text.toString()))
            {
                editTextPhone.setError("Enter Valid Mobile Number")
                editTextPhone.isFocusable =true
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
                    with(viewBinding){
                        val bundle = bundleOf("mobilenumber" to editTextPhone.text.toString())
                        findNavController().navigate(R.id.action_signinFrgmnt_to_otpFragment ,bundle)
                    }
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