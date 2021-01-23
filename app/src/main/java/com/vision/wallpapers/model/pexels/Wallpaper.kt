package com.vision.wallpapers.model.pexels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "wallpaper"
)
data class Wallpaper(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        val avg_color: String,
        val height: Int,
        val photographer: String,
        val photographer_id: Int,
        val photographer_url: String,
        val src: Src,
        val url: String,
        val width: Int
)