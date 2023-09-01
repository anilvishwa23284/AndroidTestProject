package com.androidtestproject.repo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import com.androidtestproject.MainApplication
import com.androidtestproject.api.ApiInterface
import com.androidtestproject.db.AuthorDatabase
import com.androidtestproject.di.DbModule
import com.androidtestproject.model.AuthorListItem
import com.androidtestproject.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AuthorPagingSource(
    private val apiInterface: ApiInterface,
    private var authorDatabase: AuthorDatabase,
    private var pageNumber: Int = STARTING_PAGE_INDEX
) : PagingSource<Int, AuthorListItem>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    var result: List<AuthorListItem>? = null

    @SuppressLint("SuspiciousIndentation")
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, AuthorListItem> {
        // Start refresh at page 1 if undefined.

        pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            Log.d("MainApp  ", "${Util.isOnline()}    ${pageNumber}")
            if (Util.isOnline()) {
                val response = withContext(Dispatchers.IO) {
                    val resultlist = apiInterface.getAuthorList(pageNumber, params.loadSize)
                    authorDatabase.getAuthorDatabase().insertAuthorList(resultlist.body()!!)
                    resultlist
                }
                result = response.body()
                Log.d("MainApp", "${result?.size}")
                LoadResult.Page(
                    data = result!!,
                    prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                    nextKey = if (result!!.isEmpty()) null else pageNumber + 1
                )
            }else{
                val response = withContext(Dispatchers.IO) {
                    val resultlist =  authorDatabase.getAuthorDatabase().getAuthorListDetail()
                    resultlist
                }
                result = response
                Log.d("MainApp", "${result?.size}")
                LoadResult.Page(
                    data = result!!,
                    prevKey = null ,
                    nextKey =null
                )
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }



}