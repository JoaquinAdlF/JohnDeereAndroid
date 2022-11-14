package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de respuesta para una solicitud de inserción en el API.
 */

data class insertResponse (
    @SerializedName("fieldCount") val fieldCount: Int?,
    @SerializedName("affectedRows") val affectedRows: Int?,
    @SerializedName("insertId") val insertId: Int?,
    @SerializedName("serverStatus") val serverStatus: Int?,
    @SerializedName("warningCount") val warningCount: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("protocol41") val protocol41: Boolean?,
    @SerializedName("changedRows") val changedRows: Int?
)