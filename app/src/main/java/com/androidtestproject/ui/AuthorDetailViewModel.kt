package com.androidtestproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidtestproject.model.AuthorListItem

class AuthorDetailViewModel:ViewModel() {


    private val _authorDetail = MutableLiveData<AuthorListItem>().apply { value = null}
    val authorDetail:LiveData<AuthorListItem>
    get() = _authorDetail


    fun setAuthorDetail(authorListItem: AuthorListItem){
        _authorDetail.value =authorListItem
    }

}