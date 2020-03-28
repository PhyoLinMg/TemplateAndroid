package com.elemental.templateapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.elemental.templateapplication.utils.Constants
import com.elemental.templateapplication.utils.MySharedPreference
import com.elemental.templateapplication.utils.ProgressUtil
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


        MySharedPreference.saveTokenSharedPreference(this,Constants.token)

        Log.d("token",MySharedPreference.getTokenFromPreference(this))

        viewModel.load()
        viewModel.getData().observe(this, Observer {
            Log.d("getDataFromActivity",it.toString())
        })
        viewModel.loadDetail(1)
        Log.d("detail",viewModel.getDetail().toString())



        ProgressUtil.returnStatus(this,viewModel.getDataState(),progress_bar)

    }


}
