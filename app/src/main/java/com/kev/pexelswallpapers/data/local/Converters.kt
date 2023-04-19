package com.kev.pexelswallpapers.data.local

import androidx.room.TypeConverter
import com.kev.pexelswallpapers.domain.model.Src

class Converters {

    @TypeConverter
    fun fromSrc(src: Src): String {
        return src.portrait
    }

    @TypeConverter
    fun toSrc(portraitUrl: String): Src {
        return Src(portraitUrl)
    }
}
