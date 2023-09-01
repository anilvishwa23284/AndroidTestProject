package com.androidtestproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidtestproject.repo.MainRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val mainRepository: MainRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) ->MainViewModel(mainRepository) as T
            modelClass.isAssignableFrom(AuthorDetailViewModel::class.java) -> AuthorDetailViewModel() as T
            else -> throw  java.lang.IllegalArgumentException(" UnKnown ViewModel class:"+modelClass.name)
        }
    }

}