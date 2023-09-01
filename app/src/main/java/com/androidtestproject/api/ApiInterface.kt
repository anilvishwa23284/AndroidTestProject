package com.androidtestproject.api

import com.androidtestproject.model.AuthorListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/list")
    suspend fun getAuthorList(
        @Query("page") page:Int,
        @Query("limit") limit: Int
    ):Response<List<AuthorListItem>>

}