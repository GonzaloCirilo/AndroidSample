package com.gcirilo.androidsample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gcirilo.androidsample.R
import com.gcirilo.androidsample.core.entities.Post
import com.gcirilo.androidsample.databinding.PostRecyclerItemBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var posts = emptyArray<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: PostRecyclerItemBinding = DataBindingUtil.inflate(inflater, R.layout.post_recycler_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.post = posts[position]
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = posts.size

    inner class ViewHolder(val binding: PostRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

}