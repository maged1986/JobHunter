package com.magednan.jobhunter.ui.fragments.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.jobhunter.R
import com.magednan.jobhunter.adapters.JobsAdapter
import com.magednan.jobhunter.adapters.UrgentJobAdapter
import com.magednan.jobhunter.databinding.UrgentJobsFragmentBinding
import com.magednan.jobhunter.viewmodels.CrrateJobViewModel
import com.magednan.jobhunter.viewmodels.JobsViewModel
import com.magednan.jobhunter.viewmodels.UrgentJobViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UrgentJobFragment : Fragment(R.layout.urgent_jobs_fragment) {

    private lateinit var binding: UrgentJobsFragmentBinding
    private val viewModel by viewModels<UrgentJobViewModel>()
    lateinit var jobsAdapter: UrgentJobAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= UrgentJobsFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.urgentJobFragFab.setOnClickListener {
            findNavController().navigate(R.id.action_urgent_jobs_fragment_to_create_job_fragment)
        }
        jobsAdapter= context?.let { UrgentJobAdapter(it) }!!
        val linearLayoutManager= LinearLayoutManager(context)
        binding.urgentJobFragRv.apply {
            setHasFixedSize(true)
            layoutManager=linearLayoutManager
            adapter=jobsAdapter
        }
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.getJobs().observe(viewLifecycleOwner, Observer {
                jobsAdapter.submitData(lifecycle,it)
            })}
    }

}