package com.thedancercodes.android.circlenotes.persistence

import androidx.room.TypeConverter

/**
 * This is a helper object to convert lists of Strings to a single String
 * and back again for the purposes of storing in the database.
 */
object StringListConverter {

    @TypeConverter
    @JvmStatic
    fun stringListToString(list: MutableList<String>?): String? =
        list?.joinToString(separator = "|")

    @TypeConverter
    @JvmStatic
    fun stringToStringList(string: String?): MutableList<String>? =
        if (!string.isNullOrEmpty()) string?.split("|")?.toMutableList()
        else mutableListOf()
  }