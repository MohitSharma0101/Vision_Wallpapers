package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel

class CategoriesFragment:Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: WallpaperViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

         viewModel.getAlphaCategoryList()

    }
}