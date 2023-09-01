package com.androidtestproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtestproject.model.AuthorListItem

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorList(authorListItem: List<AuthorListItem>)

    @Query("SELECT *FROM AuthorListItem" )
    suspend fun getAuthorListDetail():List<AuthorListItem>

}