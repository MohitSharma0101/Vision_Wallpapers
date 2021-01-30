package com.vision.wallpapers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem
import com.vision.wallpapers.model.pexels.Wallpaper


@Database(
        entities = [AlphaPhotoResponseItem::class],
        version = 1
)
@TypeConverters(Converter::class)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun getWallpaperDao(): WallpaperDao

    companion object {
        @Volatile
        private var instance: WallpaperDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                WallpaperDatabase::class.java,
                "wallpaper_db.db"
        ).build()
    }
}