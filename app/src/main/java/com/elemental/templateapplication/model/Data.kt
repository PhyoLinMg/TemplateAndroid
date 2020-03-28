package com.elemental.templateapplication.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("major_id")
    val majorId: Int,
    @SerializedName("major_name")
    val majorName: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("subject_name")
    val subjectName: String
)