package com.vp.task.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vp.task.R
import com.vp.task.viewmodel.UserMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: UserMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[UserMainViewModel::class.java]
        mainViewModel.getUserListResponse()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        mainViewModel.getUserListResponse()

        mainViewModel.response.observe(this){
            val ul = it.data!!.iterator()
            while (ul.hasNext())
            {
                Log.d("userList",ul.next().username)

            }
        }
    }
}