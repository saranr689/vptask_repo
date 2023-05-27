package com.vp.task.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.task.R
import com.vp.task.databinding.HomeFragmentBinding
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.ui.activity.UserDetailActivity
import com.vp.task.ui.adapter.UsersListAdapter
import com.vp.task.utils.CheckInternetConnection
import com.vp.task.viewmodel.UserMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*the class used to display user list
**/
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
        //checking internet connectivity if it's connected api call trigger to get users list else show no internet view
        val checkInternetConnection by lazy { CheckInternetConnection(requireActivity().application) }
        checkInternetConnection.observe(requireActivity()) {
            if (it) {
                binding.rvUserslist.visibility = View.VISIBLE
                binding.tvNoInterne.visibility = View.GONE
                initRecyclerView()
                fetchUsersList()
            } else {
                binding.rvUserslist.visibility = View.GONE
                binding.tvNoInterne.visibility = View.VISIBLE
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }

    }
    /*This method fetch userlist and details
    * api call to fetch userslist
    * */
    private fun fetchUsersList() {
        mainViewModel.getUserListResponse()
        mainViewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is BaseNetworkCallResult.Success -> {
                    hideProgressBar()
                    Log.d("_log_D", "SUCCESS" + it.data.toString())
                    usersListAdapter.setUserList(it.data!!)
                }
                is BaseNetworkCallResult.Error -> {
                    hideProgressBar()
                    Log.d("_D_D", "ERROR")
                }
                is BaseNetworkCallResult.Loading -> {
                    showProgressBar()
                }

                else -> {}
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbView.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.pbView.visibility = View.VISIBLE
    }
    /*initilaize recyclerview to user lists*/
    private fun initRecyclerView() {
        binding.rvUserslist.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = usersListAdapter
        }
//        user list on item click passing user id to userdetailActivity class
        usersListAdapter.setOnItemClickListener {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra("user_list", it as java.io.Serializable)
            startActivity(intent)
        }
    }
}