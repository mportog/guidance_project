package com.mportog.guidanceprojecttest.model.dto

import com.google.gson.annotations.SerializedName
import com.mportog.guidanceprojecttest.model.ui.User

internal data class UserDto(
    @SerializedName("img") val img: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String?
)

internal fun UserDto.toUser() = User(
    img = this.img ?: "",
    name = this.name ?: "",
    username = this.username ?: ""
)