package com.vp.task.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.databinding.CommentsListItemBinding
import com.vp.task.model.PostComments
import com.vp.task.model.PostCommentsItem
import com.vp.task.model.UsersListItem
/*The class used to display post comments item list */
class PostsCommentListAdapter() :
    RecyclerView.Adapter<PostsCommentListAdapter.PostsListCommentViewHolder>() {


    var postComments: List<PostCommentsItem> = PostComments()

    /*this method used to get post comments where its trigger onCommentclickevent of postlist adapter */
    fun setCommentList(postComments: PostComments) {
        this.postComments = postComments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListCommentViewHolder {
        val binding = CommentsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsListCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsListCommentViewHolder, position: Int) {
        holder.bind(postComments[position])
    }

    override fun getItemCount(): Int {
        Log.d("_log_size_D", postComments.count().toString())
        return postComments.size
    }

    inner class PostsListCommentViewHolder(private val binding: CommentsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postCommentsItem: PostCommentsItem) {
            binding.tvComments.text = postCommentsItem.body
            binding.tvCommentsName.text = postCommentsItem.name
        }
    }

}