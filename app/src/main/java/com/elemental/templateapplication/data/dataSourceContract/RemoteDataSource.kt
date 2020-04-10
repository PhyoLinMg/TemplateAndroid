package com.elemental.templateapplication.data.dataSourceContract

import com.elemental.templateapplication.data.model.Majors
import com.elemental.templateapplication.data.model.Periods
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface RemoteDataSource {
    fun getMajors(): Deferred<Response<Majors>>
    fun getPeriods():Deferred<Response<Periods>>
}