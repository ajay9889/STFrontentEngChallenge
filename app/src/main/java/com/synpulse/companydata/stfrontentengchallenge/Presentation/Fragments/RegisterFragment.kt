package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.UserSignInViewModel
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.RegisterBinding
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<RegisterBinding>(RegisterBinding::inflate) {
    val userSignInViewModel: UserSignInViewModel by inject()
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
            signIn.setOnClickListener {
                if (findNavController().previousBackStackEntry?.destination?.id == R.id.signinFrgmnt){
                    findNavController().popBackStack()
                }else{
                    findNavController().navigate(R.id.action_registerFrgmnt_to_signinFrgmnt)
                }
            }
            buttonSignUP.setOnClickListener {
                if(validateField())
                userSignInViewModel.onSignup(requireActivity(), editTextEmail.text.toString(),editTextPassword.text.toString())
            }
        }
    }

    fun validateField(): Boolean{

       with(viewBinding){
           editTextEmail.setError(null)
           editTextPassword.setError(null)
            if(editTextEmail.text.toString().isNullOrBlank())
            {
                editTextEmail.setError("Enter Email")
                editTextEmail.isFocusable =true
                return false
            }else if(!Utils.isValidEmail(editTextEmail.text.toString()))
             {
               editTextEmail.setError("Enter Valid Email")
               editTextEmail.isFocusable =true
               return false
             }else  if(editTextPassword.text.toString().isNullOrBlank())
               {
                   editTextEmail.setError("Enter Password")
                   editTextEmail.isFocusable =true
                   return false
               }else  if(editTextPassword.text.toString().length<6)
               {
                   editTextPassword.setError("Password length at least 6 character")
                   editTextEmail.isFocusable =true
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
                is ViewState.Content -> {
                    dialog?.cancel()
                    findNavController().navigate(R.id.action_registerFrgmnt_to_mobileFragment)
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