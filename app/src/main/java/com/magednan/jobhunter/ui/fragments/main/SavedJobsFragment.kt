package com.magednan.jobhunter.ui.fragments.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.magednan.jobhunter.databinding.SavedJobsFragmentBinding
import com.magednan.jobhunter.viewmodels.JobsViewModel
import com.magednan.jobhunter.viewmodels.SavedJobsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedJobsFragment : Fragment(R.layout.saved_jobs_fragment) {
    private lateinit var binding: SavedJobsFragmentBinding
    val viewModel by viewModels<SavedJobsViewModel>()
    lateinit var jobsAdapter: JobsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SavedJobsFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context)


        binding.savedJobFargRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            jobsAdapter = JobsAdapter(context)
            adapter = jobsAdapter
        }
        lifecycleScope.launch() {
            viewModel.getJobs().observe(viewLifecycleOwner, Observer {
                jobsAdapter.submitData(lifecycle, it)
                Log.d("TAG", "onActivityCreatedSaved: $it")
            })
        }
        jobsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)
            }
            findNavController().navigate(
                R.id.action_jobsFragment_to_jobDetailsFragment, bundle
            )
        }
    }

}