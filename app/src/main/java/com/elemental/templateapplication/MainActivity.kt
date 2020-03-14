package com.elemental.templateapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elemental.templateapplication.utils.MySharedPreference
import com.elemental.templateapplication.utils.kodeinViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val viewModel:SampleViewModel by kodeinViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        viewModel.showToast()

        MySharedPreference.postStringSharedPreference(this,"This is saving")

        Log.d("savedValue",MySharedPreference.getStringFromPreference(this))



    }
}
