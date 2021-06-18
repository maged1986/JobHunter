package com.magednan.jobhunter.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.network.JobHuntingAPI
import com.magednan.jobhunter.utils.NetworkState
import java.io.IOException
import javax.inject.Inject
private const val JOBS_PAGE_INDEX=1

class JobsDataSource @Inject constructor(
        private val jobHuntingAPI: JobHuntingAPI
) :PagingSource<Int,Job>(){
    private val _isLoaded= MutableLiveData<LoadResult<Int, Job>>()
    val isLoaded: LiveData<LoadResult<Int, Job>>
        get() = _isLoaded


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Job> {
        val position = params.key ?: JOBS_PAGE_INDEX

        return try {
            val response = jobHuntingAPI.getRemoteJob()
            val jobs = response.body()!!.jobs
            Log.d("TAG", "load: "+jobs)

            LoadResult.Page(
                    data = jobs,
                    prevKey = if (position == JOBS_PAGE_INDEX) null else position - 1,
                    nextKey = if (jobs.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Job>): Int? {
        TODO("Not yet implemented")
    }

}