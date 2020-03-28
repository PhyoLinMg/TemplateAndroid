package com.elemental.templateapplication.utils

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {

    private fun getSharedPreference(context: Context):SharedPreferences{
        return context.getSharedPreferences(Constants.mySharedPreference,Context.MODE_PRIVATE)
    }

    fun postStringSharedPreference(context:Context,something:String){
        with(getSharedPreference(context).edit()){
            putString(Constants.String_keyword,something)
            commit()
        }
    }
    fun getStringFromPreference(context:Context):String{
        val sharedPreferences= getSharedPreference(context)
        return sharedPreferences.getString(Constants.String_keyword,"").toString()
    }
    fun saveTokenSharedPreference(context:Context,token:String){
        with(getSharedPreference(context).edit()){
            putString(Constants.access_token,token)
            commit()
        }
    }
    fun getTokenFromPreference(context:Context):String{
        val sharedPreferences= getSharedPreference(context)
        return sharedPreferences.getString(Constants.access_token,"").toString()


    }


}