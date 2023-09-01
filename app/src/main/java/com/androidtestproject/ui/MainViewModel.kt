package com.androidtestproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.androidtestproject.model.AuthorListItem
import com.androidtestproject.repo.MainRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {

    fun getAuthorList():Flow<PagingData<AuthorListItem>>{
        return mainRepository.getAuthor().cachedIn(viewModelScope)
    }


}