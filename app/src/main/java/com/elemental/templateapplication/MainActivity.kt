package com.elemental.templateapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elemental.templateapplication.utils.MySharedPreference
import com.elemental.templateapplication.utils.Status
import com.elemental.templateapplication.utils.kodeinViewModel
import kotlinx.android.synthetic.main.activity_main.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val viewModel:SampleViewModel by kodeinViewModel()
//    private val useCase:TestUseCase by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        useCase.load()
//
//
//
//        Log.d("getList",useCase.get().toString())

        viewModel.load()
        viewModel.getData()
        viewModel.loadDetail(1)
        Log.d("detail",viewModel.getDetail().toString())

        MySharedPreference.postStringSharedPreference(this,"This is saving")

        Log.d("savedValue",MySharedPreference.getStringFromPreference(this))

        Status.returnStatus(this,viewModel.getDataState(),progress_bar)

    }


}
