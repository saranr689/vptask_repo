package com.vp.task.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vp.task.R
import com.vp.task.databinding.ActivityMainBinding
import com.vp.task.utils.CheckInternetConnection
import dagger.hilt.android.AndroidEntryPoint

/*the class used to set userlist-> home fragment
* bottomnavigation controller*/
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
        supportActionBar!!.title = getString(R.string.users_list)
    }

    override fun onBackPressed() {
        finish()
    }

}