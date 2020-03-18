package com.elemental.templateapplication.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object StatusReturn {
    fun status(lifecyclerowner: LifecycleOwner, status:LiveData<STATUS>){
        status.observe(lifecyclerowner, Observer {
            when(it){
                STATUS.FAILED->{

                }
                STATUS.LOADED->{

                }
                STATUS.LOADING->{

                }

            }
        })
    }
}