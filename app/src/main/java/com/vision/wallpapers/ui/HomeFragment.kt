package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentHomeBinding
import com.vision.wallpapers.util.Resources

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter
    lateinit var viewModel: WallpaperViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.height = (height / 2.5).toInt()
                return true
            }
        }

        adapter = Adapter()
        setupRecyclerView(binding.homeRecyclerView, gridLayoutManager, adapter)

        getUnsplashPhotos()

        binding.searchEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchUnsplash(binding.searchEditText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun setupRecyclerView(
            recyclerView: RecyclerView,
            gridLayoutManager: GridLayoutManager,
            adapter: Adapter
    ) {
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    private fun getUnsplashPhotos() {

        viewModel.getUnsplashPhotos()

        viewModel.unsplashPhotos.observe(viewLifecycleOwner, Observer { photos ->
            when (photos) {
                is Resources.Success -> {
                    photos.data?.let {
                        adapter.differ.submitList(it)
                    }
                }
            }
        })
    }

    private fun searchUnsplash(query: String) {

        viewModel.searchUnsplashPhotos(query)

        viewModel.unsplashSearchPhotos.observe(viewLifecycleOwner, Observer { searchResults ->
            when (searchResults) {
                is Resources.Success -> {
                    searchResults.data?.let {
                        adapter.differ.submitList(it)
                    }
                }
            }
        })
    }
}