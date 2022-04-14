package com.gcirilo.androidsample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gcirilo.androidsample.R
import com.gcirilo.androidsample.core.entities.User
import com.gcirilo.androidsample.databinding.UserRecyclerItemBinding
import com.gcirilo.androidsample.ui.fragments.UsersFragmentDirections

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var users = emptyArray<User>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: UserRecyclerItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.user_recycler_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.user = users[position]
            binding.showPostsButton.setOnClickListener {
                // Navigate to User's Posts
                val action = UsersFragmentDirections
                    .actionUsersFragmentToUserPostsFragment(users[position].id)
                itemView.findNavController().navigate(action)
            }
            binding.executePendingBindings()
        }
    }

    inner class ViewHolder(val binding: UserRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)
}