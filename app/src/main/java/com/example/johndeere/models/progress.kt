package com.example.johndeere.models

import com.google.gson.annotations.SerializedName

/*
Modelo de Progreso para un empleado.
 */

data class progress (
    @SerializedName("id") val id: Int?,
    @SerializedName("level1") val level1: Int?,
    @SerializedName("level2") val level2: Int?,
    @SerializedName("level3") val level3: Int?,
    @SerializedName("employeeid") val employeeid: Int?
        )