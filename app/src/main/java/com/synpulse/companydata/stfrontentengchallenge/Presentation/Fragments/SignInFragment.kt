package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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

            editTextPhone.setOnEditorActionListener(object :TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    event?.let {
                        if (actionId == EditorInfo.IME_ACTION_DONE
                            || event.getAction() == KeyEvent.ACTION_DOWN
                            || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if(validateField())
                                userSignInViewModel.onPhoneNumberVerificationsIn(requireActivity(), editTextPhone.text.toString() );

                            return true;
                        }
                    }
                    return false;
                }
            })
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_signin_to_register)
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
            }else if(!PhoneNumberUtils.isGlobalPhoneNumber(editTextPhone.text.toString()))
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
                        findNavController().navigate(R.id.action_signinFrgmnt_to_otpFragment ,Bundle().apply {
                            putString("mobilenumber" , editTextPhone.text.toString())
                            putParcelable("tokenPAP" ,userSignInViewModel.tokenPAP)
                            putString("verificationPhoneId" ,userSignInViewModel.verificationPhoneId)
                        })
                    }
                }
                is ViewState.Content -> {
                    dialog?.cancel()
                    findNavController().navigate(R.id.action_signinFrgmnt_to_homeFragment)
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message?.let { it1 ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning),
                            it1,"Okay")
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