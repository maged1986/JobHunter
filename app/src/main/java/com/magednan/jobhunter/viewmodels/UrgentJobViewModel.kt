package com.magednan.jobhunter.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.models.UrgentJob
import com.magednan.jobhunter.repository.JobsRepository

class UrgentJobViewModel @ViewModelInject constructor(
    val repository: JobsRepository
) : ViewModel() {
    suspend fun getJobs(): LiveData<PagingData<UrgentJob>> {
        return  repository.getUrgentJobs().cachedIn(viewModelScope)
    }
}