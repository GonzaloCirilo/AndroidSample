package com.gcirilo.androidsample.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gcirilo.androidsample.R
import com.gcirilo.androidsample.databinding.UserPostsFragmentBinding
import com.gcirilo.androidsample.ui.adapter.PostAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.posts.collect{
                        adapter.posts = it.toTypedArray()
                    }
                }
                launch{
                    viewModel.errorMessage.collect { error ->
                        context?.also {
                            Snackbar.make(it, view, error, Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }
}