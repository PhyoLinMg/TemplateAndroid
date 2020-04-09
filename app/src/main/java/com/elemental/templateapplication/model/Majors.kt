package com.elemental.templateapplication.model


import com.google.gson.annotations.SerializedName

data class Majors(
    @SerializedName("data")
    val majors: List<Major>
) {
    data class Major(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("year")
        val year: Int
    )
}