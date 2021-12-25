package com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard

import androidx.lifecycle.AndroidViewModel
import com.synpulse.companydata.stfrontentengchallenge.DataSource.repository.FinancialDataReposityImpl
import com.synpulse.companydata.stfrontentengchallenge.MainApplication

class HomeViewModel(val application: MainApplication, val finRepository : FinancialDataReposityImpl): AndroidViewModel(application){

}