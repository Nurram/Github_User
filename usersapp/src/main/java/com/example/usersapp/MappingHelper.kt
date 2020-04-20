package com.example.usersapp

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import com.example.usersapp.model.User

object MappingHelper {
    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        Log.d("TAG", "User cursor $usersCursor")

        usersCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val username = getString(getColumnIndexOrThrow("login"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
                val htmlUrl = getString(getColumnIndexOrThrow("html_url"))
                val reposUrl = getString(getColumnIndexOrThrow("repos_url"))
                val starredUrl = getString(getColumnIndexOrThrow("starred_url"))
                userList.add(User(id, username, avatarUrl, htmlUrl, reposUrl, starredUrl))
            }
        }

        Log.d("TAG", "User list $userList")
        return userList
    }

    fun mapCursorToObject(notesCursor: Cursor?): User? {
        var user: User? = null
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow("id"))
            val username = getString(getColumnIndexOrThrow("login"))
            val avatarUrl = getString(getColumnIndexOrThrow("avatar_url"))
            val htmlUrl = getString(getColumnIndexOrThrow("html_url"))
            val reposUrl = getString(getColumnIndexOrThrow("repos_url"))
            val starredUrl = getString(getColumnIndexOrThrow("starred_url"))
            user = User(id, username, avatarUrl, htmlUrl, reposUrl, starredUrl)
        }

        return user
    }

    fun userToValue(user1: User): ContentValues {
        val values = ContentValues()
        values.put("id", user1.id)
        values.put("login", user1.login)
        values.put("avatar_url", user1.avatar_url)
        values.put("html_url", user1.html_url)
        values.put("repos_url", user1.repos_url)
        values.put("starred_url", user1.starred_url)

        return values
    }

    fun valueToUser(values: ContentValues): User {
        return User(
            values.getAsInteger("id"), values.getAsString("login"),
            values.getAsString("avatar_url"), values.getAsString("html_url"),
            values.getAsString("repos_url"), values.getAsString("starred_url"))
    }
}