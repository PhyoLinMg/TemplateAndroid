package com.elemental.templateapplication.remote.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.templateapplication.User
import com.elemental.templateapplication.data.dataSourceContract.RemoteDataSource
import com.elemental.templateapplication.data.model.Data
import com.elemental.templateapplication.data.model.Periods
import com.elemental.templateapplication.remote.network.services.ApiService
import com.elemental.templateapplication.utils.STATUS
import com.elemental.templateapplication.utils.networkUtils.Resource
import com.elemental.templateapplication.utils.networkUtils.networkCall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RemoteDataSourceImpl(private val api:ApiService):RemoteDataSource {
    val lists: MutableLiveData<List<Any>> = MutableLiveData()
    val status: MutableLiveData<STATUS> = MutableLiveData()
    val data: MutableLiveData<Any> = MutableLiveData()
    private var value = Any()

    override fun getAll() = networkCall<Periods,List<Any>> {
        client = api.getPeriods()
    }



    override fun getDetail(id:Int) = null




}