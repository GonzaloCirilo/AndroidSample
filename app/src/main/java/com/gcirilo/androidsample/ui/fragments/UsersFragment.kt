package com.gcirilo.androidsample.ui.fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.gcirilo.androidsample.R
import com.gcirilo.androidsample.databinding.UsersFragmentBinding
import com.gcirilo.androidsample.ui.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()

    private lateinit var binding: UsersFragmentBinding

    private val adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.users_fragment, container, false)
        binding.adapter = adapter
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.users.observe(viewLifecycleOwner) {
            adapter.users = it.toTypedArray()
            if(it.isNullOrEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
                binding.usersRecyclerView.visibility = View.GONE
            } else {
                binding.emptyView.visibility = View.GONE
                binding.usersRecyclerView.visibility = View.VISIBLE
            }

        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading->
            val transition: Transition = Slide(Gravity.TOP)
            TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // If an error occurs fetching the data Toast the error message
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

}