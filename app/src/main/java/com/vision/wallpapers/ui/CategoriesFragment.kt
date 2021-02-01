package com.vision.wallpapers.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.google.android.material.chip.Chip
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.ColorAdapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentCategoriesBinding
import com.vision.wallpapers.util.Constants.Categories
import com.vision.wallpapers.util.Constants.Colors
import com.vision.wallpapers.util.Palette
import com.vision.wallpapers.util.Resources


class CategoriesFragment:Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: WallpaperViewModel
    lateinit var viewPager: CarouselView
    lateinit var adapter: ColorAdapter
    lateinit var showAdapter: Adapter
    lateinit var gridLayout: GridLayoutManager
    lateinit var layoutManager: LinearLayoutManager
    lateinit var binding: FragmentCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        var isDropped = false

        viewPager = binding.viewPager


        setupViewPager()
        setupColorsRecyclerView()

        adapter.differ.submitList(Colors)

        showAdapter.setOnItemClickListener { image, url ->
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("url", url)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (activity as MainActivity),
                image,
                "photo"
            )
            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)
        }

        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                searchAlpha(textView.text.toString())
                binding.recentCard.visibility = View.GONE
                binding.recentText.visibility = View.GONE
                binding.colorText.visibility = View.GONE
                binding.colorRecyclerView.visibility = View.GONE
                binding.categoriesText.visibility = View.GONE
                binding.viewPager.visibility = View.GONE
                binding.showRecyclerView.visibility = View.VISIBLE

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        adapter.setOnItemClickListener { name ->
            searchAlpha(name)
            binding.searchBar.setText(name)
            binding.recentCard.visibility = View.GONE
            binding.recentText.visibility = View.GONE
            binding.colorText.visibility = View.VISIBLE
            binding.colorRecyclerView.visibility = View.VISIBLE
            binding.categoriesText.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.showRecyclerView.visibility = View.VISIBLE

        }

        binding.dropdown.setOnClickListener {
            if (!isDropped) {
                binding.dropdown.setImageResource(R.drawable.chevron_up)
                val genres = arrayOf("Thriller", "Comedy", "Adventure")
                for (genre in genres) {
                    val chip = Chip(context)
                    chip.text = genre
                    chip.maxEms = 7
                    binding.chipGroup2.addView(chip)
                    isDropped = true
                }
            } else {
                binding.dropdown.setImageResource(R.drawable.chevron_down)
                binding.chipGroup2.removeAllViews()
                val genres = arrayOf("Thriller", "Comedy", "Adventure")
                for (genre in genres) {
                    val chip = Chip(context)
                    chip.text = genre
                    chip.maxEms = 7
                    binding.chipGroup2.addView(chip)
                    isDropped = true
                }
                isDropped = false
            }

        }

        val genres = arrayOf("Thriller", "Comedy", "Adventure")
        for (genre in genres) {
            val chip = Chip(context)
            chip.text = genre
            chip.maxEms = 7
            binding.chipGroup2.addView(chip)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.categoriesText.visibility == View.GONE) {
                binding.searchBar.setText("")
                binding.showRecyclerView.visibility = View.GONE
                binding.recentCard.visibility = View.VISIBLE
                binding.recentText.visibility = View.VISIBLE
                binding.colorText.visibility = View.VISIBLE
                binding.colorRecyclerView.visibility = View.VISIBLE
                binding.categoriesText.visibility = View.VISIBLE
                binding.viewPager.visibility = View.VISIBLE
            }
        }

    }

    private fun setupColorsRecyclerView() {
        showAdapter = Adapter(viewModel)
        gridLayout = GridLayoutManager(context, 2)
        binding.showRecyclerView.layoutManager = gridLayout
        binding.showRecyclerView.adapter = showAdapter

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
            hideIndicator(false)
            indicatorAnimationType = IndicatorAnimationType.SCALE
            carouselOffset = OffsetType.START
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.categoryIv)
                imageView.load(Categories[position].count){
                    crossfade(true)
                    allowHardware(true)
                    placeholder(Color.parseColor( Palette.LIGHT[position % Palette.LIGHT.size] ).toDrawable())
                }
                val text = view.findViewById<TextView>(R.id.categoryName)
                text.text = Categories[position].name
                view.setOnClickListener {
                    searchAlpha(Categories[position].name)
                    binding.searchBar.setText(Categories[position].name)
                    binding.recentCard.visibility = View.GONE
                    binding.recentText.visibility = View.GONE
                    binding.colorText.visibility = View.VISIBLE
                    binding.colorRecyclerView.visibility = View.VISIBLE
                    binding.categoriesText.visibility = View.GONE
                    binding.viewPager.visibility = View.GONE
                    binding.showRecyclerView.visibility = View.VISIBLE
                }
            }
            show()

        }

    }

    private fun hideKeyboard() {
        val view: View? = activity?.currentFocus
        if (view != null) {
            val imm: InputMethodManager? =
                context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun searchAlpha(query: String) {
        viewModel.searchAlphaPhotos(query)
        viewModel.alphaSearchPhotos.observe(viewLifecycleOwner, {
            when (it) {
                is Resources.Success -> {
                    it.data?.let { list ->
                        showAdapter.differ.submitList(list.wallpapers)
                    }
                }
            }
        })
    }


}


