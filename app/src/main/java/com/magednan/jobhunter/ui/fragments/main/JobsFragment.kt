package com.magednan.jobhunter.ui.fragments.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.jobhunter.R
import com.magednan.jobhunter.R.anim
import com.magednan.jobhunter.adapters.JobsAdapter
import com.magednan.jobhunter.adapters.LoadStateAdapter
import com.magednan.jobhunter.databinding.JobsFragmentBinding
import com.magednan.jobhunter.viewmodels.JobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class JobsFragment : Fragment(R.layout.jobs_fragment) {
    private lateinit var binding: JobsFragmentBinding
    val viewModel by viewModels<JobsViewModel>()
    lateinit var jobsAdapter:JobsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= JobsFragmentBinding.bind(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutManager= LinearLayoutManager(context)
        binding.jobsFragRv.apply {
            setHasFixedSize(true)
            layoutManager=linearLayoutManager
            jobsAdapter=JobsAdapter(context)
            adapter=jobsAdapter
        }
        lifecycleScope.launch(Dispatchers.Main){
        viewModel.getJobs().observe(viewLifecycleOwner, Observer {
            jobsAdapter.submitData(lifecycle, it)
        })}
        jobsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)
            }
            findNavController().navigate(
                    R.id.action_jobsFragment_to_jobDetailsFragment,bundle
            )
        }
    }


}