package com.magednan.jobhunter.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.magednan.jobhunter.firebase.Firebase
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.models.UrgentJob
import java.io.IOException
import javax.inject.Inject


private const val JOBS_PAGE_INDEX=1
class UrgentJobDataSource  @Inject constructor(
    val firebase: Firebase
) : PagingSource<Int, UrgentJob>(){
    override fun getRefreshKey(state: PagingState<Int, UrgentJob>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UrgentJob> {
        val position = params.key ?: JOBS_PAGE_INDEX
        return try {
            val jobs:List<UrgentJob> = firebase.getUrgentJobs()
            Log.d("TAG", "loadgetSavedJobs: "+jobs)
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