package com.magednan.jobhunter.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.repository.JobsRepository

class JobDetailsViewModel @ViewModelInject constructor(
    val jobsRepository: JobsRepository
): ViewModel() {
    fun uploadJob(job: Job, context: Context?) {
        jobsRepository.uploadJob(job,context)
    }
}