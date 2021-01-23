package com.vision.wallpapers.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vision.wallpapers.model.pexels.Wallpaper

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(wallpaper: Wallpaper):Long

    @Query("SELECT * FROM wallpaper ORDER BY id DESC")
    fun getAllArticles():LiveData<List<Wallpaper>>

    @Delete
    suspend fun deleteWallpaper(wallpaper: Wallpaper)

    @Query("DELETE FROM wallpaper")
    suspend fun deleteAllWallpaper()
}