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
import com.elemental.templateapplication.utils.kodeinViewModel
import com.elemental.templateapplication.utils.networkUtils.Resource
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(),KodeinAware {
    override val kodein: Kodein by kodein()
    private val viewModel: SampleViewModel by kodeinViewModel()
//    private val useCase:TestUseCase by instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MySharedPreference.postStringToSharedPreference(this,Constants.token,Constants.access_token)

        Log.d("token",MySharedPreference.getStringFromPreference(this,Constants.access_token))


        viewModel.getPeriods().observe(this, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("MainActivity", "--> Loading...")
                    progress_bar.visibility= View.VISIBLE
                }
                Resource.SUCCESS -> {
                    Log.d("MainActivity", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("getDataFromActivity",it.data?.get(0).toString())
                    println("${it.data?.get(0)!!::class.simpleName}")
                    progress_bar.visibility=View.GONE

                }
                Resource.ERROR -> {
                    Log.d("MainActivity", "--> Error!")
                    Log.d("code",it.errorType.toString())
                    Toast.makeText(this,"Error occured",Toast.LENGTH_LONG).show()

                }
            }
        })





    }


}
