package com.elemental.templateapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.templateapplication.domain.useCase.TestUseCase
import com.elemental.templateapplication.utils.STATUS

//do not pass context to viewModel

class SampleViewModel(private val useCase: TestUseCase):ViewModel() {

    fun getPeriods()=
         useCase.getPeriods()

    fun getPeriodDetail(id:Int)=useCase.getPeriodDetail(id)

    
}