package com.elemental.templateapplication.data.model

import com.elemental.templateapplication.utils.networkUtils.DataResponse


data class Periods(
    val data: List<Data>
): DataResponse<List<Data>>{
    override fun retrieveData(): List<Data> = data
}