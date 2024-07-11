package com.example.database.utils

import androidx.room.TypeConverter
import java.text.DateFormat
import java.util.Date

internal class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): Date? = value?.let { DateFormat.getDateTimeInstance().parse(value) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? = date?.time?.let { DateFormat.getDateTimeInstance().format(it) }
}