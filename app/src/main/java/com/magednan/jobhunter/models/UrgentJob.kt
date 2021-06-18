package com.magednan.jobhunter.models

data class UrgentJob (
    val hrAgent:String?,
    val details:String?,
    val telPhone:String?,
    val email:String?,
    val company:String?
    ){
    constructor():this("","","","","")
}
