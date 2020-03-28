package com.elemental.templateapplication.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.GetService
import com.elemental.templateapplication.User
import com.elemental.templateapplication.utils.STATUS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TestRepositoryImpl(val context: Context,val api:GetService) : TestRepository{

    val lists:MutableLiveData<List<Any>> = MutableLiveData()
    val status:MutableLiveData<STATUS> = MutableLiveData()
    private var value=Any()
    override fun load() {
        status.postValue(STATUS.LOADING)
        GlobalScope.launch {
            try {
                val response=api.getPeriods().await()
                Log.d("response",response.body()!!.toString())
                when{
                    response.isSuccessful->{
                        lists.postValue(response.body()!!.data)
                        status.postValue(STATUS.LOADED)
                    }
                }
            }catch (e:NoConnectivityException){
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
        value=User("gg","gg")
    }

    override fun getDetail(): Any {
        return value
    }

    override fun getStatus(): LiveData<STATUS> {
        return status
    }


}