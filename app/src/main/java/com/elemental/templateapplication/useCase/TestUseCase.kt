package com.elemental.templateapplication.useCase

import com.elemental.templateapplication.repository.TestRepository

class TestUseCase constructor(private val testRepository: TestRepository) {
    fun load(){
        testRepository.load()
    }
    fun get():List<Any>{
        return testRepository.get()
    }
}