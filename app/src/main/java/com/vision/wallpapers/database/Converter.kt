package com.vision.wallpapers.database

import androidx.room.TypeConverter
import com.vision.wallpapers.model.Src

class Converter {

    @TypeConverter
    fun fromSource(s:Src):String = s.landscape

    @TypeConverter
    fun toSource(l:String):Src = Src(l,l,l,l,l,l,l,l)
}