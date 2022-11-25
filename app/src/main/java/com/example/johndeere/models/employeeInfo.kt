package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de Empleado.
 */

data class employeeInfo (
    @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("companyname") val companyname: String?,
    @SerializedName("companycode") val companycode: String?,
    @SerializedName("organizationid") val organizationid: Int?,
    @SerializedName("level1") val level1: Int?,
    @SerializedName("level2") val level2: Int?,
    @SerializedName("level3") val level3: Int?,
    @SerializedName("message") val message: String?
        )