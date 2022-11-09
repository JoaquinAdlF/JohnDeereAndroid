package com.example.johndeere

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categories (
    val name : String,
    val subCategory : List<Words>

        ) : Parcelable {
}