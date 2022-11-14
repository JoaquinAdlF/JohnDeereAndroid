package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de Organización.
 */

data class organization (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("companycode") val companycode: String?,
        )