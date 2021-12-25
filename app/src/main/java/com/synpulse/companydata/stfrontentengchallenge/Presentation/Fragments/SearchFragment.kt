package com.synpulse.companydata.stfrontentengchallenge.Presentation.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.algolia.search.saas.*
import com.synpulse.companydata.Core.apputils.DsAlert
import com.synpulse.companydata.Core.base.BaseFragment
import com.synpulse.companydata.stfrontentengchallenge.BuildConfig
import com.synpulse.companydata.stfrontentengchallenge.Core.Util.Utils
import com.synpulse.companydata.stfrontentengchallenge.Presentation.Adapter.CompanyListAdapter
import com.synpulse.companydata.stfrontentengchallenge.Presentation.ViewModels.dashboard.SearchViewModel
import com.synpulse.companydata.stfrontentengchallenge.databinding.SearchFragmentBinding
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {
    companion object{
        fun getFragmentInstance(): Pair<String, Bundle>{
            return Pair(SearchFragment::class.java.name , Bundle())
        }
    }
    var dialog: ProgressDialog? = null
    val searchViewModel: SearchViewModel by inject()
    lateinit var client : Client
    lateinit var  index: Index ;
    private val _textInput = BehaviorSubject.create<String?>()
    private val textInput = _textInput.toFlowable(BackpressureStrategy.LATEST)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextInputObserver()
        setUPView();
    }

    private fun setTextInputObserver() {
        client = Client(BuildConfig.ALGO_API_KEY, BuildConfig.ALGO_SEARCHAPI_KEY)
        index  = client.getIndex("companydata")
        index.setSettingsAsync(JSONObject().apply {
            put("searchableAttributes", "name")
            put("searchableAttributes", "symbol")
            put("searchableAttributes", "company")
        }, completionHandler)

        /*
        * Created behavioural object to manage the stream input using Rx Android observable
        * */
        compositeDisposable.add(
            textInput.debounce(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe{
                    index.searchAsync(Query(it), completionHandler)
                }
        )
    }

    val completionHandler: CompletionHandler = object : CompletionHandler {
        override fun requestCompleted(content: JSONObject?, error: AlgoliaException?) {
            content?.let {
                if(content.has("hits"))
                {
                    val arrayHits =it.getJSONArray("hits")
                    var arrayList = arrayListOf<String>()
                    for (i in 0 until arrayHits.length()) {
                        arrayHits.getJSONObject(i)?.let { item ->
                            "${item.getString("symbol")} \n${item.getString("name")}".let { keywords ->
                                if (!arrayList.contains(keywords))
                                    arrayList.add(keywords)
                            }
                        }
                    }
                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.select_dialog_item,
                        arrayList.toTypedArray())

                    with(viewBinding){
                        autoCompleteTextView.setThreshold(2)
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView .setOnItemClickListener(object: AdapterView.OnItemClickListener{
                            override fun onItemClick(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                Utils.hideKeyboard(requireContext() ,autoCompleteTextView );
                                adapter.getItem(position)?.let {
                                    if(it.length>0)
                                    searchViewModel.getSearchCompanyName(it.split(" ")[0].trim())
                                }
                            }
                        });
                    }
                }
            }

        }
    }


    fun setUPView(){
        with(viewBinding) {
            dialog = DsAlert.onCreateDialog(requireContext())
            dialog?.show()
            autoCompleteTextView.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().length>0){
                        _textInput.onNext(s.toString())
                    }else{
                        searchViewModel.getSearchCompanyName("")
                    }
                }
            });


        }

        initRecyclerView()
        searchViewModel.getSearchCompanyName("")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.cancel()
    }
    @SuppressLint("CheckResult")
    fun initRecyclerView(){
        with(viewBinding){
           Utils.itemGridListDecore(requireContext(),recyclerviewDashboardHome)
            val itemRecords = CompanyListAdapter(requireContext())
            recyclerviewDashboardHome.adapter=itemRecords
            searchViewModel.getDefaultCompanyList.observe(viewLifecycleOwner , {
                dialog?.cancel()
                searchViewModel.getPaggingSourceData(it).subscribe(
                     { pagingdata ->
                        (recyclerviewDashboardHome.adapter as CompanyListAdapter).submitData(
                            lifecycle,
                            pagingdata
                        )
                    }
                )
            })
        }
    }

}