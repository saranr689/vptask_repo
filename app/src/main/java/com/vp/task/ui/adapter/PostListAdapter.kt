package com.vp.task.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.R
import com.vp.task.databinding.PostItemListBinding
import com.vp.task.model.UsersPosts
import com.vp.task.model.UsersPostsItem

/*The class PostListAdapter used to list out the users post items
* updating selected post comments*/
class PostListAdapter() : RecyclerView.Adapter<PostListAdapter.UserPostListListViewHolder>() {

    var userPost: List<UsersPostsItem> = UsersPosts()
    lateinit var context: Context
    //this variable used to oncomment click view event updates
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
        return userPost.size
    }

    //this method helps to recive and set listner to intercat between adapter and userdetails fragment
    fun setOnCommemtClick(listener: (String, RecyclerView) -> Unit) {
        onCommentClickEvent = listener
    }

    //this method used to update user post lists item
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
