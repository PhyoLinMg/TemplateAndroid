package com.elemental.templateapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.templateapplication.domain.useCase.TestUseCase
import com.elemental.templateapplication.utils.STATUS

//do not pass context to viewModel

class SampleViewModel(private val useCase: TestUseCase):ViewModel() {

    fun load(){
        useCase.load()
    }
    fun getData():LiveData<List<Any>>{
        return useCase.get()
    }
    fun getDataState(): LiveData<STATUS> {
        return useCase.getDataLoadState()
    }
    fun getDetail():Any{
        return useCase.getDetail()
    }
    fun loadDetail(id:Int){
        return useCase.loadDetail(id)
    }
}