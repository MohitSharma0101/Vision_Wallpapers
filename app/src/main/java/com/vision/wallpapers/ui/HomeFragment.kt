package com.vision.wallpapers.ui

import android.os.Bundle
import android.util.Log
import android.view.View
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


        viewModel.getCuratedWallpapers()

        gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.height = (height / 2.5).toInt()
                return true
            }
        }

        adapter = Adapter()
        setupRecyclerView(binding.homeRecyclerView, gridLayoutManager, adapter)



        viewModel.curatedWallpapers.observe(viewLifecycleOwner, Observer { list ->
            when (list) {
                is Resources.Success -> {
                    list.data?.let {
                        adapter.differ.submitList(it.photos)
                        Log.d("URL", it.photos.toString())
                    }

                }
            }
        })

    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        gridLayoutManager: GridLayoutManager,
        adapter: Adapter
    ) {
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }
}