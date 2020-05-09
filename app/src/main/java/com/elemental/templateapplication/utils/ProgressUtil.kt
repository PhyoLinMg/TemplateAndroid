package com.elemental.templateapplication.utils

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.elemental.templateapplication.utils.networkUtils.Resource

object ProgressUtil {
    fun returnStatus(viewLifecycleOwner: LifecycleOwner,status: LiveData<STATUS>,progressbar:ProgressBar){
        status.observe(viewLifecycleOwner, Observer {
            when(it){
                STATUS.LOADING->{
                    progressbar.visibility=View.VISIBLE
                }
                STATUS.LOADED->{
                    progressbar.visibility=View.GONE
                }
                STATUS.FAILED->{
                    progressbar.visibility=View.VISIBLE
                }
                STATUS.ERROR->{
                    Log.d("error","error")
                }
                else->{

                }
            }


        })
    }
}