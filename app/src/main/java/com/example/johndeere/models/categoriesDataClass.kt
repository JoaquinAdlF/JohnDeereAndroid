package com.example.johndeere

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Modelo de Categorías principales.
Contiene el nombre de la categoría y la lista de palabras que le pertenecen.
 */

@Parcelize
data class Categories (
    val name : String,
    val subCategory : List<Words>

        ) : Parcelable {
}