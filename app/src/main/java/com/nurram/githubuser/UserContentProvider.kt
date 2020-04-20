package com.nurram.githubuser

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.nurram.githubuser.model.User
import com.nurram.githubuser.model.User.Companion.AUTHORITY
import com.nurram.githubuser.model.User.Companion.CONTENT_URI
import com.nurram.githubuser.room.MyDatabase

class UserContentProvider : ContentProvider() {


    companion object {
        private const val USERS = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var db: MyDatabase

        init {

            sUriMatcher.addURI(AUTHORITY, "user_table", USERS)

            sUriMatcher.addURI(
                AUTHORITY,
                "user_table/#",
                USER_ID
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int? = db.userDao().deleteById(uri.lastPathSegment!!.toInt())
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted!!
    }

    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        db = MyDatabase.getInstance(context as Context)!!

        val user: User = if (values != null) {
            MappingHelper.valueToUser(values)
        } else {
            User(
                0, "login", "avatar_url", "html_url",
                "repos_url", "starred_url"
            )
        }

        val addData = when (USERS) {
            sUriMatcher.match(uri) -> db.userDao().insertUser(user)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$addData")
    }

    override fun onCreate(): Boolean {
        db = MyDatabase.getInstance(context as Context)!!
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when (sUriMatcher.match(uri)) {
            USERS -> db.userDao().getAllUsersToCursor()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}
