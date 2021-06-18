package com.magednan.jobhunter.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.jobhunter.R
import com.magednan.jobhunter.adapters.JobsAdapter
import com.magednan.jobhunter.databinding.SearchJobFragmentBinding
import com.magednan.jobhunter.viewmodels.SearchJobViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchJobFragment : Fragment(R.layout.search_job_fragment) {
private lateinit var binding:SearchJobFragmentBinding
    val arg: SearchJobFragmentArgs by navArgs()
    val viewModel by viewModels<SearchJobViewModel>()
    lateinit var jobsAdapter: JobsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= SearchJobFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var searchItem =arg.searchItem
            val linearLayoutManager= LinearLayoutManager(context)
        //    jobsAdapter= JobsAdapter()
            binding.sfragRv.apply {
                setHasFixedSize(true)
                layoutManager=linearLayoutManager
                itemAnimator = null
                adapter=jobsAdapter
            }
lifecycleScope.launch(){
    viewModel.searchJobs(searchItem).observe(viewLifecycleOwner, Observer {
        jobsAdapter.submitData(lifecycle, it)
    })}


        jobsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("job", it)
            }
            findNavController().navigate(
                    R.id.action_searchJobFragment_to_jobDetailsFragment,bundle
            )

        }
    }

}