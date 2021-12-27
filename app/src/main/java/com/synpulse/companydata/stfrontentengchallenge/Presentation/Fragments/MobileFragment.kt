package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Activity.HomeActivity
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.MobilenumberfragmentBinding
import org.koin.android.ext.android.inject

class MobileFragment : BaseFragment<MobilenumberfragmentBinding>(MobilenumberfragmentBinding::inflate) {
    val mobileOTPViewModel: UserSignInViewModel by inject()
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
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
            btnOTP.setOnClickListener {
                if(validateField())
                    mobileOTPViewModel.onPhoneNumberVerificationsIn(requireActivity(), editTextPhone.text.toString() );
            }

            editTextPhone.setOnEditorActionListener(object : TextView.OnEditorActionListener{
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_DONE &&  !editTextPhone.text.toString().isNullOrBlank()) {
                            if(validateField())
                            mobileOTPViewModel.onPhoneNumberVerificationsIn(requireActivity(), editTextPhone.text.toString() );
                            return true;
                        }
                    return false;
                }
            })
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
        mobileOTPViewModel.registeredUserInfo.observe(viewLifecycleOwner , {
            when(it){
                is ViewState.Loading -> {
                    dialog?.show()
                }
                is ViewState.verificationCodeToken -> {
                    dialog?.cancel()
                    with(viewBinding){
                        findNavController().navigate(R.id.action_mobileFragment_to_otpFragment ,Bundle().apply {
                            putString("mobilenumber" , editTextPhone.text.toString())
                            putParcelable("tokenPAP" ,mobileOTPViewModel.tokenPAP)
                            putString("verificationPhoneId" ,mobileOTPViewModel.verificationPhoneId)
                        })
                    }
                }

                is ViewState.Content -> {
                    dialog?.cancel()
                    requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                    requireActivity().finish()
                }
                is ViewState.Message -> {
                    dialog?.cancel()
                    it.message.let { msg ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning), msg,"Okay")
                    }
                }
                is ViewState.Error -> {
                    dialog?.cancel()
                    it.t.message?.let { message ->
                        DsAlert.showAlert(requireActivity(), getString(R.string.warning), message,"Okay")
                    }
                }
                else -> {
                    dialog?.cancel()
                }
            }
        })
    }
}