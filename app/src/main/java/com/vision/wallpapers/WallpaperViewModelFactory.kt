package com.vision.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vision.wallpapers.repository.WallpaperRepo

class WallpaperViewModelFactory(val repo: WallpaperRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WallpaperViewModel(repo) as T
    }


}