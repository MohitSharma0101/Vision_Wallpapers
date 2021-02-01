package com.vision.wallpapers.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vision.wallpapers.Adapter
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.databinding.FragmentFavBinding

class FavFragment : Fragment(R.layout.fragment_fav) {
    lateinit var binding: FragmentFavBinding
    lateinit var viewModel: WallpaperViewModel
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        gridLayoutManager = GridLayoutManager(context, 2)
        adapter = Adapter(viewModel)
        binding.favRecyclerView.layoutManager = gridLayoutManager
        binding.favRecyclerView.adapter = adapter

        adapter.setOnItemClickListener { image, url, photo ->
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("photo", photo)
            val bundle = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(),
                android.R.anim.fade_in,
                android.R.anim.fade_out
            ).toBundle()
            startActivity(intent, bundle)
        }
        binding.deleteBtn.setOnClickListener {
            confirmDeleteAlert()
        }

        viewModel.getSavedWallpaper().observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.noWallpapers.visibility = View.VISIBLE
            }
            adapter.differ.submitList(it)
            Log.d("mohit", it.toString())
        })


    }
    private fun confirmDeleteAlert() =
            MaterialAlertDialogBuilder(requireContext(), R.style.CustomMaterialDialog)
                    .setIcon(R.drawable.ic_trash)
                    .setTitle("Delete All Wallpapers")
                    .setMessage("Are you sure You want to delete All Wallpaper?")
                    .setPositiveButton("Yes") { _, _ ->
                        viewModel.deleteAllWallpaper()
                    }
                    .setNegativeButton("No") { _, _ ->
                    }
                    .show()
}