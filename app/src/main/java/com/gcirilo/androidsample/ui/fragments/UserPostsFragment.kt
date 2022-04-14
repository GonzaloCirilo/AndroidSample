package com.gcirilo.androidsample.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.gcirilo.androidsample.R
import com.gcirilo.androidsample.databinding.UserPostsFragmentBinding
import com.gcirilo.androidsample.ui.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPostsFragment : Fragment() {

    private val viewModel: UserPostsViewModel by viewModels()

    private lateinit var binding: UserPostsFragmentBinding

    private val adapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_posts_fragment, container, false)
        binding.viewModel = viewModel
        binding.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
            binding.executePendingBindings()
        }

        viewModel.posts.observe(viewLifecycleOwner) {
            adapter.posts = it.toTypedArray()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}