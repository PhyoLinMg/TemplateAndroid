package com.elemental.templateapplication.useCase

import androidx.lifecycle.LiveData
import com.elemental.templateapplication.repository.TestRepository
import com.elemental.templateapplication.utils.STATUS

class TestUseCase constructor(private val testRepository: TestRepository) {
    fun load(){
        testRepository.load()
    }
    fun get():List<Any>{
        return testRepository.get()
    }
    fun getDataLoadState():LiveData<STATUS>{
        return testRepository.getStatus()
    }
    fun getDetail():Any{
        return testRepository.getDetail()
    }
    fun loadDetail(id:Int){
        return testRepository.loadDetail(id)
    }
}