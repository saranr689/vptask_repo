package com.vp.task.ui

import com.vp.task.model.UsersListItem

interface UsersClickEvent {

    fun onItemClick(userItem : UsersListItem)
}