
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


class CategoriesFragment : Fragment(R.layout.fragment_categories) {

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
    var isDropped = false
    private var recentSearchList = ArrayList<String?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        isDropped = false

        viewPager = binding.viewPager


        setupViewPager()
        setupColorsRecyclerView()
        getRecentSearches()
        handelBackPressed()


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
                getRecentSearches(true)
                isDropped = true
            } else {
                binding.dropdown.setImageResource(R.drawable.chevron_down)
                getRecentSearches(false)
                isDropped = false
            }
        }

    }

    private fun handelBackPressed() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            isDropped = false
            binding.dropdown.setImageResource(R.drawable.chevron_down)
            binding.noSearch.visibility = View.GONE
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

    private fun getRecentSearches(isFull: Boolean = false) {
        recentSearchList.clear()
        loadRecentList()
        if (recentSearchList.isEmpty()) {
            recentSearchList = arrayListOf("Thriller", "Comedy", "Adventure")
        }
        val recent = recentSearchList.asReversed()
        binding.chipGroup2.removeAllViews()
        var size = recent.size
        if (recent.size > 4 && !isFull) {
            size = 4
        } else if (recent.size > 10 && isFull) {
            size = 10
        }
        for (i in 0 until size) {
            if (!recent[i].isNullOrEmpty())
                createChip(recent[i])
        }
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
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
                imageView.load(Categories[position].count) {
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
        hideKeyboard()
        binding.noSearch.visibility = View.GONE
        recentSearchList.add(query)
        saveRecentList()
        viewModel.alphaSearchResponse = null
        viewModel.searchAlphaPhotos(query)
        viewModel.alphaSearchPhotos.observe(viewLifecycleOwner, {
            when (it) {
                is Resources.Success -> {
                    it.data?.let { list ->
                        isLoading = false
                        binding.categoryProgressBar.visibility = View.GONE
                        getRecentSearches()
                        if (list.wallpapers != null) {
                            showAdapter.differ.submitList(list.wallpapers.toList())
                        } else {
                            binding.noSearch.visibility = View.VISIBLE
                            showAdapter.differ.submitList(null)
                        }
                    }
                }
                is Resources.Loading -> {
                    binding.categoryProgressBar.visibility = View.VISIBLE
                    isLoading = true
                }
            }

        })

    }


    private fun saveRecentList(): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val mEdit1 = sp.edit()
        mEdit1.putInt("Status_size", recentSearchList.size)
        for (i in 0 until recentSearchList.size) {
            mEdit1.remove(i.toString())
            mEdit1.putString(i.toString(), recentSearchList[i])
        }
        return mEdit1.commit()
    }

    private fun loadRecentList() {
        val mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val size = mSharedPreference1.getInt("Status_size", 0)
        for (i in 0 until size) {
            recentSearchList.add(mSharedPreference1.getString(i.toString(), null))
        }
    }

    private fun createChip(text: String?) {
        val chip = Chip(context)
        chip.text = text
        chip.maxEms = 7
        chip.setOnClickListener {
            searchAlpha((it as Chip).text.toString())
            binding.recentCard.visibility = View.GONE
            binding.recentText.visibility = View.GONE
            binding.colorText.visibility = View.GONE
            binding.colorRecyclerView.visibility = View.GONE
            binding.categoriesText.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.showRecyclerView.visibility = View.VISIBLE
        }
        binding.chipGroup2.addView(chip)


    }


}


