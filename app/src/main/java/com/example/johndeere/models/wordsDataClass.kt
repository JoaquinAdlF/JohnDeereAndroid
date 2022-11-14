package com.example.johndeere

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Modelo de subcategorías (palabras).
Contiene el nombre de la palabra y la url de acceso dentro del proyecto.
 */

@Parcelize
data class Words (
    val name : String,
    val localVideoURL : Int?
        ) : Parcelable