package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.CompanyListData.Companion.toCompanyListDomain
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseDailyData
import com.synpulse.companydata.stfrontentengchallenge.DataSource.module.TimeSerieseData
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.HomeViewModel
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.FragmentDetailBinding
import org.json.JSONObject
import org.koin.android.ext.android.inject
import android.view.MenuInflater
import android.view.MenuItem


class DetailsFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    companion object{
        const val EX_DATA="ex_company_data"
        fun getFragmentInstance(companyListData: CompanyListData): Pair<String, Bundle>{
            return Pair(DetailsFragment::class.java.name , Bundle().apply {
                putSerializable(EX_DATA, companyListData)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    var fav: MenuItem? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        fav = menu.add(1,1,1,"Share")
        fav?.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
        fav?.setIcon(R.drawable.ic_baseline_ios_share_24);
        fav?.setOnMenuItemClickListener(object :MenuItem.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
               when(item.itemId){
                   1->{
                       val sendIntent: Intent = Intent().apply {
                           action = Intent.ACTION_SEND
                           putExtra(Intent.EXTRA_TEXT, "Financial instrument informations for "+companyListData.name +"("+ companyListData.symbol+")")
                           type = "text/plain"
                       }

                       val shareIntent = Intent.createChooser(sendIntent, "Sharing Trade Interests")
                       startActivity(shareIntent)
                       return true
                   }
               }
                return false
            }
        })
    }

    var dialog: ProgressDialog? = null
    val homeViewModel: HomeViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUPView()
        observeLiveData()
    }
    fun observeLiveData(){
        homeViewModel.getTimeSerieseData.observe(viewLifecycleOwner , {
            dialog?.cancel()
            when(it){
             is ViewState.Content->{
                 with(viewBinding){
                     symbol.text = it.data.metaData?.symbol ?: companyListData.symbol
                     name.text = it.data.metaData?.information ?: companyListData.name
                     name.ellipsize = TextUtils.TruncateAt.MARQUEE
                     name.isSelected = true
                     name.marqueeRepeatLimit = -1
                 }
                 it.data.let {
                     createTimeSeriesChart(it)
                 }
             }
            }
        })
        homeViewModel.getGlobalQoutes.observe(viewLifecycleOwner , {
           it?.let {
               with(viewBinding){
                   it.globalQuote?.let {
                       it.changePercent?.let {
                           changePercent.text = it
                           if(it.contains("-".toRegex())){
                               changePercent.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                           }
                       }
                       homeViewModel.insertGlobalQouteInDb(it)
                   }

               }
           }
        })
    }
    lateinit var companyListData :CompanyListData
    fun setUPView(){
        companyListData = arguments?.getSerializable(EX_DATA) as CompanyListData
        dialog = DsAlert.onCreateDialog(requireContext())
        dialog?.show()
        homeViewModel.getTimeSeriesData(companyListData.symbol)
        homeViewModel.getGlobalQoutes(companyListData.symbol)
        with(viewBinding){
            btnFollow.setOnClickListener {
                val singleItems=companyListData.toCompanyListDomain(homeViewModel.dbInstance)
                homeViewModel.dbInstance.RoomDataAccessObejct().insertSingle(singleItems)
                if(singleItems.isFollwoing.equals("1")) {
                    btnFollow.text = requireContext().resources.getString(R.string.followed)
                } else {
                    btnFollow.text = requireContext().resources.getString(R.string.add_follow)
                }
            }
            if(companyListData.isFollwoing.equals("1")) {
                btnFollow.text = requireContext().resources.getString(R.string.followed)
            } else {
                btnFollow.text = requireContext().resources.getString(R.string.add_follow)
            }
        }
    }

    fun createTimeSeriesChart(timeSerieseData: TimeSerieseData){
       try{
           JSONObject(timeSerieseData.time_series_data?.toString())?.let {tseriesdata->
               val keys: Iterator<String> = tseriesdata.keys()
               Log.e("detail company", "tseriesdata keys.hasNext()= " + keys.hasNext())

               var items = ArrayList<TimeSerieseDailyData>()
               var labels = ArrayList<String>()
               while (keys.hasNext()) {
                   val key = keys.next()
                   if (tseriesdata.get(key) is JSONObject) {
                       // do something with jsonObject here
                       var gson = Gson()
                       val item: TimeSerieseDailyData = gson.fromJson(
                           tseriesdata.get(key).toString(),
                           TimeSerieseDailyData::class.java
                       )
                       items.add(item)
                       labels.add(key)
                   }
               }
               setLineChartData(items,labels)
           }
       }catch (e:Exception){e.printStackTrace()}
    }
    fun setLineChartData(_items : ArrayList<TimeSerieseDailyData>, _labels : ArrayList<String>) {
        val linevalues = ArrayList<Entry>()
        var labels = ArrayList<String>()
        var k = 0f
        for (i in 0 until _labels.size) {
            linevalues.add(Entry(k, _items.get(i).close.toFloat()))
            labels.add(_labels[i])
            k++
        }
        val linedataset = LineDataSet(linevalues, "Close")
        //We add features to our chart
        linedataset.color = resources.getColor(R.color.purple_200)
        linedataset.circleRadius =2f
        linedataset.setDrawFilled(true)
        linedataset.valueTextSize =2F
        linedataset.fillColor = resources.getColor(R.color.green)
        linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //We connect our data to the UI Screen
        val data = LineData(linedataset)
        with(viewBinding){
            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            lineChart.data = data
            lineChart.setBackgroundColor(resources.getColor(R.color.white))
            lineChart.animateXY(2000, 2000, Easing.EaseInCubic)
        }
    }
}