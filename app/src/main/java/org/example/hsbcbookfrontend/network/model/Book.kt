package org.example.hsbcbookfrontend.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val name: String,
    val author: String,
    val intro: String
) : Parcelable
