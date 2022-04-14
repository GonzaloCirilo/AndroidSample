package com.gcirilo.androidsample.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {

    @BindingAdapter(value = ["setAdapter"])
    @JvmStatic
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>){
        this.run {
            this.layoutManager = LinearLayoutManager(context.applicationContext)
            this.adapter = adapter
        }
    }
}