package com.vision.wallpapers.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
import java.lang.reflect.Type


class CategoriesFragment:Fragment(R.layout.fragment_categories) {

    lateinit var viewModel: WallpaperViewModel
    lateinit var viewPager: CarouselView
    lateinit var adapter: ColorAdapter
    lateinit var showAdapter: Adapter
    lateinit var gridLayout: GridLayoutManager
    lateinit var layoutManager: LinearLayoutManager
    lateinit var binding: FragmentCategoriesBinding
    lateinit var searchQuery: String
    var isLoading = false
    var isScrolling = false
    private val recentSearchList = ArrayList<String?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        var isDropped = false

        viewPager = binding.viewPager


        setupViewPager()
        setupColorsRecyclerView()
        getRecentSearches()


        adapter.differ.submitList(Colors)

        showAdapter.setOnItemClickListener { image, url, photo ->
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("photo", photo)
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
                searchQuery = textView.text.toString()
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
            searchQuery = name
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

    private fun getRecentSearches(){
        var recent = getArrayList()
        if(recent == null){
            recent = arrayListOf("Thriller", "Comedy", "Adventure")
        }
        for (genre in recent) {
            val chip = Chip(context)
            chip.text = genre
            chip.maxEms = 7
            binding.chipGroup2.addView(chip)
        }
    }


    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount: Int = gridLayout.childCount
            val totalItemCount: Int = gridLayout.itemCount
            val firstVisibleItemPosition: Int = gridLayout.findFirstVisibleItemPosition()
            if (!isLoading && !viewModel.alphaLastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0 && totalItemCount >= 30 && isScrolling
                ) {
                    viewModel.searchAlphaPhotos(searchQuery, viewModel.alphaSearchPage)
                    isScrolling = false
                }
            }

        }
    }


    private fun setupColorsRecyclerView() {
        showAdapter = Adapter(viewModel)
        gridLayout = GridLayoutManager(context, 2)
        binding.showRecyclerView.layoutManager = gridLayout
        binding.showRecyclerView.adapter = showAdapter
        binding.showRecyclerView.addOnScrollListener(recyclerViewOnScrollListener)

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
                    placeholder(
                        Color.parseColor(Palette.LIGHT[position % Palette.LIGHT.size]).toDrawable()
                    )
                }
                val text = view.findViewById<TextView>(R.id.categoryName)
                text.text = Categories[position].name
                view.setOnClickListener {
                    searchQuery = Categories[position].name
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
        recentSearchList.add(query)
        saveArrayList(recentSearchList)
        viewModel.alphaSearchResponse = null
        viewModel.searchAlphaPhotos(query)
        viewModel.alphaSearchPhotos.observe(viewLifecycleOwner, {
            when (it) {
                is Resources.Success -> {
                    it.data?.let { list ->
                        isLoading = false
                        binding.categoryProgressBar.visibility = View.GONE
                        showAdapter.differ.submitList(list.wallpapers.toList())
                    }
                }
                is Resources.Loading -> {
                    binding.categoryProgressBar.visibility = View.VISIBLE
                    isLoading = true
                }
            }
        })
        recentSearchList.add(query)
        saveArrayList(recentSearchList)
    }

    private fun saveArrayList(list: ArrayList<String?>?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("recent", json)
        editor.apply()
    }
    private fun getArrayList(): ArrayList<String?>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val gson = Gson()
        val json = prefs.getString("recent", null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }


}


