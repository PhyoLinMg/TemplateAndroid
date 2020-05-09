package com.elemental.templateapplication.data.dataSourceContract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.templateapplication.data.model.Data
import com.elemental.templateapplication.data.model.Majors
import com.elemental.templateapplication.data.model.Periods
import com.elemental.templateapplication.utils.STATUS
import com.elemental.templateapplication.utils.networkUtils.Resource
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface RemoteDataSource {
    fun getAll():LiveData<Resource<List<Any>>>
    fun getDetail(id:Int):LiveData<Resource<Any>>?
}