package com.vp.task.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.databinding.CommentsListItemBinding
import com.vp.task.model.PostComments
import com.vp.task.model.PostCommentsItem
import com.vp.task.model.UsersListItem

class PostsCommentListAdapter() :
    RecyclerView.Adapter<PostsCommentListAdapter.PostsListCommentViewHolder>() {


    var postComments: List<PostCommentsItem> = PostComments()
    private var onItemClickListener: ((UsersListItem) -> Unit)? = null

    fun setCommentList(postComments: PostComments) {
        this.postComments = postComments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListCommentViewHolder {
        val binding =
            CommentsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsListCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsListCommentViewHolder, position: Int) {
        holder.bind(postComments[position])
    }

    override fun getItemCount(): Int {
        Log.d("_log_size_D", postComments.count().toString())
        return postComments.size
    }

    fun setOnItemClickListener(listener: (UsersListItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class PostsListCommentViewHolder(private val binding: CommentsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postCommentsItem: PostCommentsItem) {
            binding.tvComments.text = postCommentsItem.body
        }

    }

}