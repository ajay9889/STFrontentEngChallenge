package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.RegisterBinding

class RegisterFragment : BaseFragment<RegisterBinding>(RegisterBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }
//    https://firebase.google.com/docs/auth/android/start
    fun setUp(){
    with(viewBinding){
        signIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFrgmnt_to_signinFrgmnt)
        }
        buttonSignUP.setOnClickListener {
            findNavController().navigate(R.id.action_registerFrgmnt_to_otpFragment)
        }
    }
    }
}