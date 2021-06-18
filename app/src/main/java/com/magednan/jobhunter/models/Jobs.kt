package com.magednan.jobhunter.models

import com.google.gson.annotations.SerializedName

data class Jobs(
        @SerializedName("job-count")
        val jobCount: Int?,
        val jobs: List<Job>,
        val legalNotice: String?
)