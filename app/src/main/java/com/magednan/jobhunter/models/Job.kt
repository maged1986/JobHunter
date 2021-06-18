package com.magednan.jobhunter.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.inject.Inject

data class Job (
    @SerializedName("candidate_required_location")
    val candidateRequiredLocation: String?,
    val category: String?,
    @SerializedName("company_logo_url")
    val companyLogoUrl: String?,
    @SerializedName("company_name")
    val companyName: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @SerializedName("job_type")
    val jobType: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    val salary: String?,
    val title: String?,
    val url: String?
):Serializable{
  constructor():this(
      "","", "",
      "" +
              "", "", -1, "", "",
      "", "", "")
}

