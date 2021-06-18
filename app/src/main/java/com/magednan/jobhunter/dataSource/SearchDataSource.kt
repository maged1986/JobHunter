package com.magednan.jobhunter.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.network.JobHuntingAPI
import java.io.IOException
import javax.inject.Inject


private const val JOBS_PAGE_INDEX=1
class SearchDataSource @Inject constructor(
        private val query: String,
        private val jobHuntingAPI: JobHuntingAPI
) : PagingSource<Int, Job>(){
    override fun getRefreshKey(state: PagingState<Int, Job>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Job> {
        val position = params.key ?: JOBS_PAGE_INDEX
        return try {
            val response = jobHuntingAPI.searchRemoteJob(query)
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


}