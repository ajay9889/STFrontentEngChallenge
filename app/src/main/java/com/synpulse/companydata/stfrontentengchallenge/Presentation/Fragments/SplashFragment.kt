package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Activity.HomeActivity
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
        try{
//            Log.d("USERS" ,"$user , PHONE: ${user?.phoneNumber}")
            if (user != null && !user.phoneNumber.isNullOrBlank()) {
                requireActivity().startActivity(Intent(context, HomeActivity::class.java))
                requireActivity().finish()
            } else {
                // User is not signed in
                if (findNavController().previousBackStackEntry?.destination?.id == R.id.signinFrgmnt){
                    findNavController().popBackStack()
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_signinFrgmnt)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            requireActivity().finish()
        }
    }
}