package com.vp.task.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.R
import com.vp.task.databinding.PostItemListBinding
import com.vp.task.model.PostComments
import com.vp.task.model.UsersPosts
import com.vp.task.model.UsersPostsItem

class PostListAdapter() : RecyclerView.Adapter<PostListAdapter.UserPostListListViewHolder>() {

    var postComments: PostComments = PostComments()
    var userPost: List<UsersPostsItem> = UsersPosts()
    lateinit var context: Context
    private var onCommentClickEvent: ((String, RecyclerView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostListListViewHolder {
        context = parent.context
        val binding =
            PostItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserPostListListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserPostListListViewHolder, position: Int) {
        holder.bind(userPost[position], position)
    }

    override fun getItemCount(): Int {
        Log.d("_log_size_D", userPost.count().toString())
        return userPost.size
    }

    fun setOnCommemtClick(listener: (String, RecyclerView) -> Unit) {
        onCommentClickEvent = listener
    }

    fun setPostList(userPost: UsersPosts) {
        this.userPost = userPost
        notifyDataSetChanged()
    }

    inner class UserPostListListViewHolder(private val binding: PostItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userPostItem: UsersPostsItem, position: Int) {
            binding.tvPostTitle.text = userPostItem.title
            binding.tcPostBody.text = userPostItem.body
            binding.tvShowComments.setOnClickListener {
//                val postsCommentListAdapter = PostsCommentListAdapter()
//                binding.rvComments.adapter = postsCommentListAdapter
//                binding.rvComments.layoutManager = LinearLayoutManager(context)
//                postsCommentListAdapter.setCommentList(postComments)
                if (binding.rvComments.isShown) {
                    binding.rvComments.visibility = View.GONE
                    binding.tvShowComments.text = context.getString(R.string.show_comments)
                } else {
                    onCommentClickEvent?.invoke(userPostItem.id.toString(), binding.rvComments)
                    binding.rvComments.visibility = View.VISIBLE
                    binding.tvShowComments.text = context.getString(R.string.hide)
                }
            }
        }
    }
}
