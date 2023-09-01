package com.androidtestproject.di

import com.androidtestproject.ui.AuthorDetailActivity
import com.androidtestproject.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetworkModule::class,DbModule::class])
interface Appcomponent {
    fun inject(mainActivity: MainActivity)
    fun inject(authorDetailActivity: AuthorDetailActivity)
}