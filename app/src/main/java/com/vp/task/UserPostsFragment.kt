package com.vp.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vp.task.databinding.FragmentPostListBinding
import com.vp.task.network.BaseNetworkCallResult
import com.vp.task.ui.PostListAdapter
import com.vp.task.ui.PostsCommentListAdapter
import com.vp.task.viewmodel.UserPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class UserPostsFragment : Fragment(), PostListAdapter.IcommentClickEvent {

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.get("user_id") as String
        Log.d("_D_userId", userId + " ")
        userPostsViewModel = ViewModelProvider(requireActivity())[UserPostsViewModel::class.java]
        userPostsViewModel.getUserPosts(userId)
        initRecyclerView()
        fetchUsersPosts(userId)
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
                is BaseNetworkCallResult.Sucess -> {
                    Log.d("_log_D", "SUCESS" + it.data.toString())
                    postListAdapter.setPostList(it.data!!)
                    _binding!!.swiperefresh.isRefreshing = false
                }
                is BaseNetworkCallResult.Error -> {
                    Log.d("_D_D", "ERROR" + it.message)
                    _binding!!.swiperefresh.isRefreshing = false
                }
            }
        }

        userPostsViewModel.userPostCommentsResponse.observe(viewLifecycleOwner)
        {
            when (it) {
                is BaseNetworkCallResult.Sucess -> {
                    Log.d("_log_D", "SUCESS" + it.data.toString())
                    postsCommentListAdapter.setCommentList(it.data!!)

                }
                is BaseNetworkCallResult.Error -> {
                    Log.d("_log_D", "ERROR" + it.data.toString())

                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvUserPosts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = postListAdapter
        }
        postListAdapter.setOnItemClickListener {
            userPostsViewModel.getPostComments(it.toString())
        }

        postListAdapter.setOnCommemtClick { postId, commentRv ->
            userPostsViewModel.getPostComments(postId)
            commentRv.adapter = postsCommentListAdapter
            commentRv.layoutManager = LinearLayoutManager(activity)
            postsCommentListAdapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCommentClickListner() {

    }
}