package com.androidtestproject.di

import androidx.room.Room
import com.androidtestproject.MainApplication
import com.androidtestproject.db.AuthorDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(var application: MainApplication) {

    @Singleton
    @Provides
    fun provideRoom(): AuthorDatabase {
        return Room.databaseBuilder(application, AuthorDatabase::class.java, "AuthorDB").build()
    }
}