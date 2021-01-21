package com.vision.wallpapers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vision.wallpapers.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        gridLayoutManager = GridLayoutManager(context, 2)
        adapter = Adapter()

        setupRecyclerView(binding.homeRecyclerView, gridLayoutManager, adapter)


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