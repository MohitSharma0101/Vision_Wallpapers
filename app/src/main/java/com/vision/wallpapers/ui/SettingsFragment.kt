package com.vision.wallpapers.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vision.wallpapers.R
import com.vision.wallpapers.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        val first = "All wallpapers in this app are from "
        val next = "<font color='#2476e3'>Wallpaper Abyss' API</font>"
        val end =
            ". If you own any of these images and want to remove from Vision Wallpapers then please contact us."
        binding.declaration.text = Html.fromHtml(first + next + end)

        binding.clearSearch.setOnClickListener {
            if (deleteSearchHistory()){
                Toast.makeText(requireContext(),"Cleared",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteSearchHistory():Boolean{
            val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val mEdit1 = sp.edit()
            val size = sp.getInt("Status_size", 0)
            for (i in 0 until size) {
             mEdit1.remove(i.toString())
        }

        return mEdit1.commit()
    }
}