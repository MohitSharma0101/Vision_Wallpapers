package com.vision.wallpapers.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(wallpaper: AlphaPhotoResponseItem):Long

    @Query("SELECT * FROM wallpaper")
    fun getAllWallpaper(): LiveData<List<AlphaPhotoResponseItem>>

    @Query("SELECT * FROM wallpaper WHERE id= :q")
    suspend fun getWallpaperByTitle( q: Int):AlphaPhotoResponseItem

    @Delete
    suspend fun deleteWallpaper(wallpaper: AlphaPhotoResponseItem)

    @Query("DELETE FROM wallpaper")
    suspend fun deleteAllWallpaper()
}