package com.elemental.templateapplication

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

class SampleViewModel(private val context: Context):ViewModel() {
    fun showToast(){
        Toast.makeText(context,"This is working with DI",Toast.LENGTH_LONG).show()
    }
}