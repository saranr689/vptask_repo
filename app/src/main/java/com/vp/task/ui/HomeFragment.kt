package com.vp.task.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.task.R
import com.vp.task.UserDetailActivity
import com.vp.task.databinding.HomeFragmentBinding
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.viewmodel.UserMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment() : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    lateinit var mainViewModel: UserMainViewModel

    @Inject
    lateinit var usersListAdapter: UsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        mainViewModel = ViewModelProvider(requireActivity())[UserMainViewModel::class.java]
        mainViewModel.getUserListResponse()
        initRecyclerView()
        fetchUsersList()
    }

    private fun fetchUsersList() {
        mainViewModel.getUserListResponse()
        mainViewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is BaseNetworkCallResult.Sucess -> {
                    Log.d("_log_D", "SUCESS" + it.data.toString())
                    usersListAdapter.setUserList(it.data!!)
                }
                is BaseNetworkCallResult.Error -> {
                    Log.d("_D_D", "ERROR")
                }

                else -> {}
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvUserslist.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = usersListAdapter
        }
        usersListAdapter.setOnItemClickListener {
            Log.d("_D_itemclick", it.name)
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra("user_list", it as java.io.Serializable)
            startActivity(intent)
        }
    }
}