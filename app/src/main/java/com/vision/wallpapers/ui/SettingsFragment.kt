package com.vision.wallpapers.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
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

        binding.contactUs.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "tilwanil818@gmail.com", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vision Wallpapers")
            context?.startActivity(Intent.createChooser(emailIntent, null))
        }

    }
}