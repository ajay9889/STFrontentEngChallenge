package com.synpulse.companydata.stfrontentengchallenge.Workmanager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mobile.data.usage.Database.Databasehelper
import com.synpulse.companydata.stfrontentengchallenge.Domain.repository.FinancialDataReposity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SyncCompanyData (context: Context , workerParameters: WorkerParameters): Worker(context,workerParameters) {
//    val reposity =inject(FinancialDataReposity::class.java).value
//    val dbInstance =inject(Databasehelper::class.java).value
    companion object{
        const val WORK_INFO_MANAGER_KEY ="WorkerSyncCompanyData"
    }
    override fun doWork(): Result {
//        CoroutineScope(Dispatchers.IO).launch {
//            reposity.getCompanyList()?.let {
//                dbInstance.RoomDataAccessObejct().insert(it)
//            }
//        }
        return Result.success()
    }

}