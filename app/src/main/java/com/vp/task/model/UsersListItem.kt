package com.vp.task.model

import java.io.Serializable

data class UsersListItem(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
) : Serializable