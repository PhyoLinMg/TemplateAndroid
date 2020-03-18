package com.elemental.templateapplication.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.templateapplication.User
import com.elemental.templateapplication.utils.STATUS

class TestRepositoryImpl(val context: Context) : TestRepository {
    val lists:MutableList<Any> = ArrayList()
    val status:MutableLiveData<STATUS> = MutableLiveData()

    override fun load() {
        //This load will come from api or db data source

        val testList:MutableList<Any> = ArrayList()
        testList.add(0, User("linmaung","123435"))
        testList.add(1, User("mgmg","what the fuck"))
        testList.add(2, User("mama","what the fuck"))
        lists.addAll(testList)
        Toast.makeText(context,lists.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun get(): List<Any> {
        return lists
    }

    override fun getStatus(): LiveData<STATUS> {
        return status
    }


}