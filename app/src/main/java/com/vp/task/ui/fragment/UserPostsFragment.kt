package com.vp.task.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.databinding.FragmentPostListBinding
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.ui.adapter.PostListAdapter
import com.vp.task.ui.adapter.PostsCommentListAdapter
import com.vp.task.viewmodel.UserPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class UserPostsFragment : Fragment() {

    private lateinit var userId: String
    lateinit var userPostsViewModel: UserPostsViewModel
    private var _binding: FragmentPostListBinding? = null

    @Inject
    lateinit var postListAdapter: PostListAdapter

    @Inject
    lateinit var postsCommentListAdapter: PostsCommentListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "MY POSTS"
        userId = arguments?.get("user_id") as String
        Log.d("_D_userId", userId + " ")
        userPostsViewModel = ViewModelProvider(requireActivity())[UserPostsViewModel::class.java]
        userPostsViewModel.getUserPosts(userId)
        fetchUsersPosts(userId)
        initRecyclerView()
        doSwipetoRefresh()
    }

    private fun doSwipetoRefresh() {
        _binding!!.swiperefresh.setOnRefreshListener {
            fetchUsersPosts(userId)
        }
    }

    private fun fetchUsersPosts(userId: String) {
        userPostsViewModel.getUserPosts(userId)
        userPostsViewModel.userPostResponse.observe(viewLifecycleOwner) {
            when (it) {
                is BaseNetworkCallResult.Success -> {
                    hideProgressBar()
                    Log.d("_log_D", "SUCESS" + it.data.toString())
                    postListAdapter.setPostList(it.data!!)
                    _binding!!.swiperefresh.isRefreshing = false
                }
                is BaseNetworkCallResult.Error -> {
                    hideProgressBar()
                    Log.d("ERROR_D", "ERROR" + it.message)
                    _binding!!.swiperefresh.isRefreshing = false
                }
                is BaseNetworkCallResult.Loading -> {
                    showProgressBar()
                    Log.d("_D_D", "Loading")
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbView.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.pbView.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.rvUserPosts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = postListAdapter
        }
        postListAdapter.setOnCommemtClick { postId, commentRv ->
            fetchUsersPostsComments(postId, commentRv)
        }

    }

    private fun fetchUsersPostsComments(postId: String, commentRv: RecyclerView) {
        userPostsViewModel.getPostComments(postId)
        userPostsViewModel.userPostCommentsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is BaseNetworkCallResult.Success -> {
                    hideProgressBar()
                    val postsCommentListAdapter = PostsCommentListAdapter()
                    commentRv.adapter = postsCommentListAdapter
                    commentRv.layoutManager = LinearLayoutManager(activity)
                    postsCommentListAdapter.setCommentList(it.data!!)
                }
                is BaseNetworkCallResult.Error -> {
                    hideProgressBar()
                    Log.d("_log_D", "ERROR" + it.data.toString())
                }
                is BaseNetworkCallResult.Loading -> {
                    showProgressBar()
                    Log.d("log_d", "Loading")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}