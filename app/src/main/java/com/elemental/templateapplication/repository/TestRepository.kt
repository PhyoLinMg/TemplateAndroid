package com.elemental.templateapplication.repository

import androidx.lifecycle.LiveData
import com.elemental.templateapplication.utils.STATUS


interface TestRepository {

    fun load()
    fun get():List<Any>
    fun getStatus():LiveData<STATUS>

}