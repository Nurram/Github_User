package com.example.usersapp.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val login: String,
    var avatar_url: String,
    var html_url: String,
    var repos_url: String,
    var starred_url: String) : Parcelable {

    companion object {
        const val AUTHORITY = "com.nuram.githubuser"
        const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath("user_table")
            .build()

    }
}