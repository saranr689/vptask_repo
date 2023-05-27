package com.vp.task.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vp.task.R
import com.vp.task.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/*the class used to set userdetails fragment*/
@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_user_detail) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)

    }
}