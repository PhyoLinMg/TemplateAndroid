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

    val lists: MutableLiveData<List<Any>> = MutableLiveData()
    val status: MutableLiveData<STATUS> = MutableLiveData()
    private var value = Any()
    override fun load() {
        status.postValue(STATUS.LOADING)
        GlobalScope.launch {
            try {
                val response=remoteDataSource.getPeriods().await()
                Log.d("response", response.toString())
                when (response.code()) {
                    200 -> {
                        lists.postValue(response.body()!!.data)
                        status.postValue(STATUS.LOADED)
                    }
                    404 -> {
                        Log.d("error", response.code().toString())
                    }
                }
            } catch (e: NoConnectivityException) {
                status.postValue(STATUS.FAILED)
            } catch (e: Throwable) {
                status.postValue(STATUS.FAILED)
            }

        }

    }

    override fun get(): LiveData<List<Any>> {
        return lists
    }

    override fun loadDetail(id: Int) {
        value = User("gg", "gg")
    }

    override fun getDetail(): Any {
        return value
    }

    override fun getStatus(): LiveData<STATUS> {
        return status
    }


}