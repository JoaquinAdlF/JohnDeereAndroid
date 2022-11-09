package com.example.johndeere

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Words (
    val name : String,
    val localVideoURL : Int?
        ) : Parcelable