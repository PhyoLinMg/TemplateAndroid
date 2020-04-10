package com.elemental.templateapplication.remote.source

import com.elemental.templateapplication.data.dataSourceContract.RemoteDataSource
import com.elemental.templateapplication.data.model.Majors
import com.elemental.templateapplication.data.model.Periods
import com.elemental.templateapplication.remote.network.services.ApiService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class RemoteDataSourceImpl(private val api:ApiService):RemoteDataSource {
    override fun getMajors(): Deferred<Response<Majors>> {
        return api.getMajors()
    }

    override fun getPeriods(): Deferred<Response<Periods>> {
        return api.getPeriods()
    }

}