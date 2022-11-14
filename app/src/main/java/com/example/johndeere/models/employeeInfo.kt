package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de Empleado.
 */

data class employeeInfo (
    @SerializedName("id") val id: Int?,
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("companycode") val companycode: String?,
    @SerializedName("organizationid") val organizationid: Int?,
    @SerializedName("level1") val level1: Int?,
    @SerializedName("level2") val level2: Int?,
    @SerializedName("level3") val level3: Int?
        )