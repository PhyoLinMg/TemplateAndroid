package com.elemental.templateapplication.domain.useCase


import com.elemental.templateapplication.domain.repository.TestRepository


class TestUseCase constructor(private val testRepository: TestRepository) {
    fun getPeriods()=
         testRepository.getAll()

    fun getPeriodDetail(id:Int)=
         testRepository.getDetail(id)



}