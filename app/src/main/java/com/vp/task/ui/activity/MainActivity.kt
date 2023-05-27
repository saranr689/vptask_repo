package com.vp.task.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vp.task.CheckInternetConnection
import com.vp.task.R
import com.vp.task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val checkInternetConnection by lazy { CheckInternetConnection(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_mainactivity) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        supportActionBar!!.title = "USERS LIST"
    }

    fun showProgress() {
        binding.pbView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        binding.pbView.visibility = View.GONE

    }

    override fun onBackPressed() {
        finish()
    }

}