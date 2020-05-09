package com.elemental.templateapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.templateapplication.remote.network.services.ApiService
import com.elemental.templateapplication.User
import com.elemental.templateapplication.data.dataSourceContract.RemoteDataSource
import com.elemental.templateapplication.domain.repository.TestRepository
import com.elemental.templateapplication.utils.STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TestRepositoryImpl(val remoteDataSource: RemoteDataSource) :
    TestRepository {

    override fun getAll() =
        remoteDataSource.getAll()


    override fun getDetail(id: Int) = remoteDataSource.getDetail(1)
}



