package com.globant.marvelapp.di

import android.content.Context
import androidx.room.Room
import com.globant.marvelapp.db.MarvelAppDataBase
import com.globant.shared.utils.Constants.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMarvelDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        MarvelAppDataBase::class.java,
        DATA_BASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideProductDao(db: MarvelAppDataBase) = db.getCharactersDao()

}