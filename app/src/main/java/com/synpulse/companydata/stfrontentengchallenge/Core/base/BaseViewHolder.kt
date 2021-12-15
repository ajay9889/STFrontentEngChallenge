package com.synpulse.companydata.Core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

typealias InflateVh<T> = (LayoutInflater, ViewGroup?, Boolean) ->T

abstract class BaseViewHolder<VB : ViewBinding> (val viewGroup: ViewGroup,
     private val inflate: InflateVh<VB>,
     protected val viewBinding: VB = inflate.invoke(LayoutInflater.from(viewGroup.context), viewGroup, false)
): RecyclerView.ViewHolder(viewBinding.root), LifecycleOwner{
    protected val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }
    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}