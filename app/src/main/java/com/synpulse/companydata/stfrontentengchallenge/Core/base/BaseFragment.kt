package com.synpulse.companydata.Core.base
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.synpulse.companydata.stfrontentengchallenge.R
import com.synpulse.companydata.stfrontentengchallenge.databinding.AppTitleBarBinding
import io.reactivex.disposables.CompositeDisposable
typealias Inflate<T> = (LayoutInflater,ViewGroup? , Boolean) ->T
abstract class BaseFragment <vb: ViewBinding>(private val infalteRoot: Inflate<vb>): Fragment(){
        // to manage the single decelarion to use in Fragment
        protected val compositeDisposable=CompositeDisposable()
        private var _binding: vb?=null
        protected val viewBinding get() = _binding!!

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
                _binding = infalteRoot.invoke(inflater ,container , false)
                return viewBinding.root

        }

        override fun onDestroyView() {
                super.onDestroyView()
                compositeDisposable.clear()
                _binding=null
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                return when(item.itemId){
                        android.R.id.home -> {
                            activity?.onBackPressed()
                            true
                        }
                        else ->{
                           super.onOptionsItemSelected(item)
                        }
                }
        }

        protected fun setupToolbar(appTitleBarBinding: AppTitleBarBinding, toolbarTitle: String = "") {
                with(appTitleBarBinding) {
                        setupToolbar(
                                toolbar = toolbar,
                                tvToolbarTitle = tvToolbarTitle,
                                toolbarTitle = toolbarTitle
                        )
                }
        }

        protected open fun setupToolbar(toolbar: Toolbar?, tvToolbarTitle: TextView? = null, toolbarTitle: String = "") {
                (activity as? AppCompatActivity)?.run {
                        toolbar?.let { toolbarSafe ->
                                setSupportActionBar(toolbarSafe)
                                supportActionBar?.run {
                                        setDisplayHomeAsUpEnabled(true)
                                        setDisplayShowHomeEnabled(true)
                                        title = ""
                                }
                                toolbarSafe.setNavigationIcon(R.drawable.ic_navigation_back_button)
                                toolbarSafe.setNavigationOnClickListener { onBackPressed() }
                                tvToolbarTitle?.text = toolbarTitle
                        }
                }
        }



}