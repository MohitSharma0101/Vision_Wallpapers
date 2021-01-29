package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.vision.wallpapers.ColorAdapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentCategoriesBinding
import com.vision.wallpapers.util.Constants.Categories
import com.vision.wallpapers.util.Constants.Colors

class CategoriesFragment:Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: WallpaperViewModel
    lateinit var viewPager: CarouselView
    lateinit var adapter: ColorAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var binding: FragmentCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        viewPager = binding.viewPager


        setupViewPager()
        setupColorsRecyclerView()

        adapter.differ.submitList(Colors)

        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchAlphaPhotos(binding.searchBar.text.toString())
                Toast.makeText(context, binding.searchBar.text.toString(), Toast.LENGTH_SHORT)
                    .show()

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        adapter.setOnItemClickListener { name ->
            viewModel.searchAlphaPhotos(name)
            Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupColorsRecyclerView() {
        adapter = ColorAdapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.colorRecyclerView.adapter = adapter
        binding.colorRecyclerView.layoutManager = layoutManager
    }

    private fun setupViewPager() {

        viewPager.apply {
            size = Categories.size
            resource = R.layout.category_card
            autoPlay = false
            scaleOnScroll = true
            enableSnapping(true)
            hideIndicator(true)
            indicatorAnimationType = IndicatorAnimationType.SCALE
            carouselOffset = OffsetType.START
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.categoryIv)
                imageView.load(Categories[position].url)
                view.setOnClickListener {
                    viewModel.searchAlphaPhotos(Categories[position].name)
                    Toast.makeText(context, Categories[position].name, Toast.LENGTH_SHORT).show()
                }
            }
            // After you finish setting up, show the CarouselView
            show()

        }

    }

}


