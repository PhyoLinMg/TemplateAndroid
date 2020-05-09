package com.elemental.templateapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.elemental.templateapplication.presentation.viewmodels.SampleViewModel
import com.elemental.templateapplication.utils.Constants
import com.elemental.templateapplication.utils.MySharedPreference
import com.elemental.templateapplication.utils.ProgressUtil
import com.elemental.templateapplication.utils.kodeinViewModel
import com.elemental.templateapplication.utils.networkUtils.Resource
import kotlinx.android.synthetic.main.activity_main.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(),KodeinAware {
    override val kodein by kodein()
    private val viewModel: SampleViewModel by kodeinViewModel()
//    private val useCase:TestUseCase by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MySharedPreference.saveTokenSharedPreference(this,Constants.token)

        Log.d("token",MySharedPreference.getTokenFromPreference(this))


        viewModel.getPeriods().observe(this, Observer {

            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("MainActivity", "--> Loading...")
                    progress_bar.visibility= View.VISIBLE
                }
                Resource.SUCCESS -> {
                    Log.d("MainActivity", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("getDataFromActivity",it.data.toString())
                    progress_bar.visibility=View.GONE

                }
                Resource.ERROR -> {
                    Log.d("MainActivity", "--> Error!")
                    Toast.makeText(this,"Error occured",Toast.LENGTH_LONG).show()

                }
            }
        })





    }


}
