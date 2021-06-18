package com.magednan.jobhunter.dataSource

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.magednan.jobhunter.firebase.Firebase
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.network.JobHuntingAPI
import java.io.IOException
import javax.inject.Inject

private const val JOBS_PAGE_INDEX=1
class SavedJobsDataSource  @Inject constructor(
     val firebase:Firebase
) : PagingSource<Int, Job>(){
    override fun getRefreshKey(state: PagingState<Int, Job>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Job> {
        val position = params.key ?: JOBS_PAGE_INDEX
        return try {
            val jobs:List<Job> = firebase.getSavedJobs()
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