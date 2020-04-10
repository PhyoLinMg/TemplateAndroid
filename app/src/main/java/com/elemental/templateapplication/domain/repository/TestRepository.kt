package com.elemental.templateapplication.domain.repository

import androidx.lifecycle.LiveData
import com.elemental.templateapplication.utils.STATUS

interface TestRepository {
    fun load()
    fun get():LiveData<List<Any>>
    fun loadDetail(id:Int)
    fun getDetail():Any
    fun getStatus():LiveData<STATUS>
}