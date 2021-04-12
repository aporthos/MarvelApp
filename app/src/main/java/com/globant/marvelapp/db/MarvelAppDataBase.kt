package com.globant.marvelapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.globant.data.datasource.local.CharactersDao
import com.globant.domain.model.CharactersDto

@Database(
    entities = [CharactersDto::class],
    version = 1
)
@TypeConverters(UrlsConverters::class)
abstract class MarvelAppDataBase: RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}