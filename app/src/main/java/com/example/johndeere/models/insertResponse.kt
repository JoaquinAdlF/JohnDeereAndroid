package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de respuesta para una solicitud de inserción en el API.
 */

data class insertResponse (
    @SerializedName("insertId") val insertId: Int?,
    @SerializedName("message") val message: String?
)