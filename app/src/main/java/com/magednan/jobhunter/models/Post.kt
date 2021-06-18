package com.magednan.jobhunter.models

data class Post (
    val userName : String?,
    val post : String?,
    val postDate : String?,
    val currentJob: String?,
    val imageUrl: String?,
    val profileImage: String?
){
    constructor():this("","","","","","")
}