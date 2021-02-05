package com.vision.wallpapers.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.text.format.Formatter
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vision.wallpapers.R
import com.vision.wallpapers.databinding.FragmentSettingsBinding
import java.io.File


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
           confirmDeleteAlert()
        }
        binding.contactUs.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "tilwanil818@gmail.com", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vision Wallpapers")
            context?.startActivity(Intent.createChooser(emailIntent, null))
        }
        binding.clearCache.setOnClickListener {
           if(context?.cacheDir?.deleteRecursively() == true){
               Toast.makeText(requireContext(), "Cleared", Toast.LENGTH_LONG).show()
               binding.tvCacheSize.text = getCacheSize()
           }
        }
        binding.tvCacheSize.text = getCacheSize()

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
    private fun getCacheSize0():String{
        var size: Long = 0
        val files:Array<File>? = requireContext().cacheDir.listFiles()
        if (files != null) {
            for (f in files) {
                size += f.length()
            }
        }

        return Formatter.formatFileSize(requireContext(), size)
    }
    private fun getCacheSize():String {
        var size: Long = 0
        size += getDirSize(requireContext().cacheDir)
        size += getDirSize(requireContext().externalCacheDir)
        return Formatter.formatFileSize(requireContext(), size)
    }

    private fun getDirSize(dir: File?): Long {
        var size: Long = 0
        for (file in dir?.listFiles()!!) {
            if (file != null && file.isDirectory) {
                size += getDirSize(file)
            } else if (file != null && file.isFile) {
                size += file.length()
            }
        }
        return size
    }
    private fun confirmDeleteAlert() =
        MaterialAlertDialogBuilder(requireContext(), R.style.CustomMaterialDialog)
            .setIcon(R.drawable.ic_trash)
            .setTitle("Delete Search History")
            .setMessage("Are you sure You want to delete all Search History?")
            .setPositiveButton("Yes") { _, _ ->
                if (deleteSearchHistory()){
                    Toast.makeText(requireContext(), "Cleared", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("No") { _, _ ->
            }
            .show()
}