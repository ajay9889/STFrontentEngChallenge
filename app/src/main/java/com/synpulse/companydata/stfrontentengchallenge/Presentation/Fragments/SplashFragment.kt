package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.SigninBinding
import com.synpulse.companydata.stfrontentengchallenge.databinding.SplashfragmentBinding

class SplashFragment : BaseFragment<SplashfragmentBinding>(SplashfragmentBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStart()
    }
    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        } else {
            // No user is signed in
            findNavController().navigate(R.id.action_splashFragment_to_registerFrgmnt)
        }
    }
}