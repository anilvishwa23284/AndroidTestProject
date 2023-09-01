package com.androidtestproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class AuthorListItem(
    @PrimaryKey(autoGenerate = true)
    val AuthorId:Int =0,
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
): Serializable