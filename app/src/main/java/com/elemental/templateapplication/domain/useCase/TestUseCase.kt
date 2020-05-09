package com.elemental.templateapplication.domain.useCase


import androidx.lifecycle.LiveData
import com.elemental.templateapplication.domain.repository.TestRepository
import com.elemental.templateapplication.utils.STATUS


class TestUseCase constructor(private val testRepository: TestRepository) {
    fun getPeriods()=
         testRepository.getAll()

    fun getPeriodDetail(id:Int)=
         testRepository.getDetail(id)



}