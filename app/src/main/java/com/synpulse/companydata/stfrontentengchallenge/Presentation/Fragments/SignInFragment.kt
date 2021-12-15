package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.SigninBinding


class SignInFragment : BaseFragment<SigninBinding>(SigninBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    fun setUp(){

        with(viewBinding){
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_signin_to_register)
            }
            buttonSignIN.setOnClickListener {
                findNavController().navigate(R.id.action_signinFrgmnt_to_homeFragment)
            }
        }
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
    }
}