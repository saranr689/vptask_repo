package com.vp.task.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vp.task.R
import com.vp.task.databinding.ActivityUserDetailBinding
import com.vp.task.model.UsersListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userDetails = intent.extras?.getSerializable("user_list") as UsersListItem
        Log.d("_D2nd", userDetails.email)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_user_detail) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)

    }
}