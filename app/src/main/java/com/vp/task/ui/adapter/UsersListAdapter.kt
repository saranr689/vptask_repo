package com.vp.task.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vp.task.databinding.UsersListBinding
import com.vp.task.model.UsersList
import com.vp.task.model.UsersListItem
/*The class used to display user list and handling clickevent to navigate to userdetais screen*/
class UsersListAdapter() : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {


    var userList: List<UsersListItem> = UsersList()
    private var onItemClickListener: ((UsersListItem) -> Unit)? = null

    //this method used to get and update userlist item
    fun setUserList(userList: UsersList) {
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        Log.d("_log_size_D", userList.count().toString())
        return userList.size
    }

    //this method call from fragment with passing listner and return args with user selected item
    fun setOnItemClickListener(listener: (UsersListItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class UsersListViewHolder(private val binding: UsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usersListItem: UsersListItem) {
            binding.tvUserName.text = usersListItem.name
            binding.tvUserName.setOnClickListener {
                onItemClickListener?.invoke(usersListItem)
            }
        }

    }

}