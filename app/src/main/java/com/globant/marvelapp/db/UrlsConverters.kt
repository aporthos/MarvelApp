package com.globant.marvelapp.db

import androidx.room.TypeConverter
import com.globant.domain.model.UrlsDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UrlsConverters {
    var gson = Gson()

    @TypeConverter
    @JvmStatic
    fun foodItemToString(foodItems: List<UrlsDto>): String = gson.toJson(foodItems)

    @TypeConverter
    @JvmStatic
    fun stringToFoodItem(data: String): List<UrlsDto> {
        val listType = object : TypeToken<List<UrlsDto>>() {
        }.type
        return gson.fromJson(data, listType)
    }
}