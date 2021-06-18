package com.magednan.jobhunter.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.magednan.jobhunter.dataSource.JobsDataSource
import com.magednan.jobhunter.dataSource.SavedJobsDataSource
import com.magednan.jobhunter.dataSource.SearchDataSource
import com.magednan.jobhunter.dataSource.UrgentJobDataSource
import com.magednan.jobhunter.firebase.Firebase
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.models.UrgentJob
import com.magednan.jobhunter.network.JobHuntingAPI
import javax.inject.Inject


class JobsRepository @Inject constructor(
    val jobHuntingAPI: JobHuntingAPI,
    val firebase: Firebase
) {


    suspend fun getJobs(): LiveData<PagingData<Job>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                maxSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { JobsDataSource(jobHuntingAPI) }
        ).liveData


    }

    suspend fun searchJobs(query: String): LiveData<PagingData<Job>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                maxSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchDataSource(query, jobHuntingAPI) }
        ).liveData
    }

    suspend fun getSavedJobs(): LiveData<PagingData<Job>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                maxSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SavedJobsDataSource(firebase) }
        ).liveData
    }
    suspend fun getUrgentJobs(): LiveData<PagingData<UrgentJob>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                maxSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UrgentJobDataSource(firebase) }
        ).liveData
    }

    fun uploadJob(job: Job, context: Context?) {
        firebase.uploadJob(job, context)
    }
 fun createAJob(urgentJob: UrgentJob, context: Context?) {
        firebase.createAJob(urgentJob, context)
    }

}