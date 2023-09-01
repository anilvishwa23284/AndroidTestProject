package com.androidtestproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidtestproject.model.AuthorListItem

@Database(entities = [AuthorListItem::class], version = 1)
abstract class AuthorDatabase:RoomDatabase() {
    abstract fun getAuthorDatabase():AuthorDao
}