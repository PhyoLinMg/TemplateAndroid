package com.elemental.templateapplication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.templateapplication.useCase.TestUseCase
import com.elemental.templateapplication.utils.STATUS

class SampleViewModel(private val context: Context,private val useCase: TestUseCase):ViewModel() {
    fun showToast(){
        Toast.makeText(context,"This is working with DI",Toast.LENGTH_LONG).show()
    }
    fun load(){
        useCase.load()
    }
    fun getData(){
        Log.d("viewModelhereList",useCase.get().toString())
    }
    fun getDataState(): LiveData<STATUS> {
        return useCase.getDataLoadState()
    }
}