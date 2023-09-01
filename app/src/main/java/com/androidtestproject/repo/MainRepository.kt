package com.androidtestproject.repo

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.androidtestproject.MainApplication
import com.androidtestproject.api.ApiInterface
import com.androidtestproject.db.AuthorDatabase
import com.androidtestproject.model.AuthorListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface,private val authorDatabase: AuthorDatabase) {

    companion object {
        private const val AUTHOR_PER_PAGE = 10
    }
    fun getAuthor(): Flow<PagingData<AuthorListItem>> {
        return Pager(
            config = PagingConfig(pageSize = AUTHOR_PER_PAGE, initialLoadSize = AUTHOR_PER_PAGE),
            pagingSourceFactory = {
                AuthorPagingSource(
                    apiInterface = apiInterface,
                    authorDatabase =authorDatabase,
                )
            }
        ).flow
    }
}