package com.vision.wallpapers.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vision.wallpapers.R
import com.vision.wallpapers.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)


    }
}