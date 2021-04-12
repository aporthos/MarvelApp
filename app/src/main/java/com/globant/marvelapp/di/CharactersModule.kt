package com.globant.marvelapp.di

import com.globant.data.datasource.local.CharactersLocalDataSourceImpl
import com.globant.data.datasource.remote.CharactersRemoteDataSourceImpl
import com.globant.data.datasource.local.CharactersDao
import com.globant.data.mapper.CharactersListMapper
import com.globant.data.repository.CharactersRepositoryImpl
import com.globant.data.services.CharactersService
import com.globant.domain.datasource.CharactersRemoteDataSource
import com.globant.domain.datasource.CharactersLocalDataSource
import com.globant.domain.repository.CharactersRepository
import com.globant.shared.domain.di.AppExecutors
import com.globant.shared.utils.NetworkUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CharactersModule {

    @Singleton
    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)

    @Singleton
    @Provides
    fun provideCharactersDataSource(
        service: CharactersService,
        mapper: CharactersListMapper
    ): CharactersRemoteDataSource =
        CharactersRemoteDataSourceImpl(
            service,
            mapper
        )


    @Singleton
    @Provides
    fun provideCharactersLocalDataSource(
        dao: CharactersDao
    ): CharactersLocalDataSource = CharactersLocalDataSourceImpl(dao)

    @Singleton
    @Provides
    fun provideCharactersRepository(
        charactersRemoteRemoteDataSource: CharactersRemoteDataSource,
        charactersLocalDataSource: CharactersLocalDataSource,
        networkUtils: NetworkUtilsImpl,
        appExecutors: AppExecutors
    ): CharactersRepository =
        CharactersRepositoryImpl(
            charactersRemoteRemoteDataSource,
            charactersLocalDataSource,
            networkUtils,
            appExecutors
        )
}