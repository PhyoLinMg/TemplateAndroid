package com.elemental.templateapplication.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.templateapplication.utils.STATUS
import com.elemental.templateapplication.utils.networkUtils.Resource

interface TestRepository {
    fun getAll(): LiveData<Resource<List<Any>>>
    fun getDetail(id:Int): LiveData<Resource<Any>>?
}