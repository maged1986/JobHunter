package com.magednan.jobhunter.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.magednan.jobhunter.models.UrgentJob
import com.magednan.jobhunter.repository.JobsRepository

class CrrateJobViewModel @ViewModelInject constructor(
    val jobsRepository: JobsRepository
): ViewModel() {
    fun createAJob(urgentJob: UrgentJob, context: Context?) {
        jobsRepository.createAJob(urgentJob, context)
    }



}