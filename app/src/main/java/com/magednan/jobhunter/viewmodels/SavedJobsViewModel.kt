package com.magednan.jobhunter.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.repository.JobsRepository

class SavedJobsViewModel @ViewModelInject constructor(
    val repository: JobsRepository
): ViewModel() {
    suspend fun getJobs(): LiveData<PagingData<Job>> {
        Log.d("TAG", "getJobs: "+repository.getSavedJobs().value)
        return  repository.getSavedJobs().cachedIn(viewModelScope)

    }
}