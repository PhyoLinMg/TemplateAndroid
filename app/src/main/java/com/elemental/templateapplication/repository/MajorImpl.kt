package com.elemental.templateapplication.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.GetService
import com.elemental.templateapplication.utils.STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MajorImpl(val context: Context, val api: GetService):TestRepository {

    val lists: MutableLiveData<List<Any>> = MutableLiveData()
    val status: MutableLiveData<STATUS> = MutableLiveData()
    override fun load() {
        status.postValue(STATUS.LOADING)
        GlobalScope.launch {
            try {
                val response=api.getMajors().await()
                Log.d("response",response.body()!!.toString())
                when{
                    response.isSuccessful->{
                        lists.postValue(response.body()!!.majors)
                        status.postValue(STATUS.LOADED)
                    }
                }
            }catch (e: NoConnectivityException){
                status.postValue(STATUS.FAILED)
            }
            catch (e:Throwable){
                status.postValue(STATUS.FAILED)
            }

        }
    }

    override fun get(): LiveData<List<Any>> {
        return lists
    }

    override fun loadDetail(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getDetail(): Any {
        TODO("Not yet implemented")
    }

    override fun getStatus(): LiveData<STATUS> {
        TODO("Not yet implemented")
    }
}