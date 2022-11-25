package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

data class updatePasswordInfo (
    @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("oldPassword") val oldPassword: String?,
    @SerializedName("newPassword") val newPassword: String?,
    @SerializedName("affectedRows") val affectedRows: Int?,
    @SerializedName("message") val message: String?
        )