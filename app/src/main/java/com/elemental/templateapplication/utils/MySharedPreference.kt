package com.elemental.templateapplication.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {

    private fun getSharedPreference(context: Context):SharedPreferences{
        return context.getSharedPreferences(Constants.mySharedPreference,Context.MODE_PRIVATE)
    }

    fun postStringToSharedPreference(context:Context,value:String,key:String){
        with(getSharedPreference(context).edit()){
            putString(key,value)
            commit()
        }
    }
    fun getStringFromPreference(context:Context,key:String):String{
        val sharedPreferences= getSharedPreference(context)
        return sharedPreferences.getString(key,"").toString()
    }



}