package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vision.wallpapers.CategoryAdapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentCategoriesBinding
import com.vision.wallpapers.util.Constants.Categories

class CategoriesFragment:Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: WallpaperViewModel
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: CategoryAdapter
    lateinit var binding: FragmentCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel


        viewModel.getAlphaCategoryList()

        setupRecycler()

        adapter.differ.submitList(Categories)


    }

    private fun setupRecycler() {

        adapter = CategoryAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        binding.categoryRecyclerView.layoutManager = linearLayoutManager
        binding.categoryRecyclerView.adapter = adapter
    }
}