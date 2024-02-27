package org.example.hsbcbookfrontend.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Long = 0,
    val name: String,
    val author: String,
    val intro: String,
    val pubYear: Int,
    val isbn: String
) : Parcelable
