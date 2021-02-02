package com.vision.wallpapers.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ramotion.circlemenu.CircleMenuView
import com.vision.wallpapers.R
import com.vision.wallpapers.WallpaperViewModel
import com.vision.wallpapers.WallpaperViewModelFactory
import com.vision.wallpapers.database.WallpaperDatabase
import com.vision.wallpapers.databinding.ActivityFullImageBinding
import com.vision.wallpapers.model.alphaCoder.AlphaPhotoResponseItem
import com.vision.wallpapers.repository.WallpaperRepo
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File


class FullImageActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    lateinit var binding: ActivityFullImageBinding
    lateinit var viewModel: WallpaperViewModel
    lateinit var url: String
    lateinit var type: String

    private val STRG_PERM = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setTheme(R.style.FullImage)
        setContentView(binding.root)

        val db = WallpaperDatabase(this)
        val repo = WallpaperRepo(db)
        val factory = WallpaperViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory).get(WallpaperViewModel::class.java)

        val photo: AlphaPhotoResponseItem =
            intent.getSerializableExtra("photo") as AlphaPhotoResponseItem
        url = photo.url_image
        type = photo.file_type
        binding.apply {
            wallpaperIv.load(url) {
                crossfade(true)
                allowHardware(true)
            }
            scaleBtn.setOnClickListener {
                adjustZoom()
            }
            rotateBtn.setOnClickListener {
                changeScreenOrientation()
            }
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        val menu = binding.circle
        menu.eventListener = object : CircleMenuView.EventListener() {
            override fun onMenuOpenAnimationStart(view: CircleMenuView) {

            }

            override fun onMenuOpenAnimationEnd(view: CircleMenuView) {

            }

            override fun onMenuCloseAnimationStart(view: CircleMenuView) {
            }

            override fun onMenuCloseAnimationEnd(view: CircleMenuView) {

            }

            override fun onButtonClickAnimationStart(view: CircleMenuView, index: Int) {

            }

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {

                when (index) {
                    0 -> {
                        requestPermission()
                    }
                    2 -> {
                        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                        viewModel.saveWallpaper(photo)

                    }
                    3 -> {
                        setWallpaper(url)
                    }

                }
            }
        }
    }

    private fun changeScreenOrientation() {
        val orientation: Int = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        if (Settings.System.getInt(
                contentResolver,
                Settings.System.ACCELEROMETER_ROTATION, 0
            ) === 1
        ) {
            val handler = Handler()
            handler.postDelayed(Runnable {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
            }, 4000)
        }
    }

    private fun adjustZoom(){
        binding.apply {
            if (wallpaperIv.scaleType == ImageView.ScaleType.CENTER_CROP) {
                scaleBtn.setImageResource(R.drawable.ic_maximize)
                wallpaperIv.scaleType = ImageView.ScaleType.FIT_CENTER
            } else {
                scaleBtn.setImageResource(R.drawable.ic_minimize)
                wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
                wallpaperIv.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    private fun downloadImageNew(filename: String = "Vision", downloadUrlOfImage: String) {
        try {
            val dm = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType(type) // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator.toString() + filename + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setWallpaper(imageUrl: String) {
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        Glide.with(applicationContext).asBitmap().load(imageUrl)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    wallpaperManager.setBitmap(resource)
                    Toast.makeText(applicationContext, "set", Toast.LENGTH_SHORT).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun requestPermission() {
        if (hasStoragePermission()) {
            downloadImageNew(downloadUrlOfImage = url)
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage so you can download wallpapers",
                STRG_PERM,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        downloadImageNew("Vision", url)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
        Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            val yes = "Permission Granted"
            val no = "Permission Denied"
            // Do something after user returned from app settings screen, like showing a Toast.
            if (hasStoragePermission()) {
                downloadImageNew(downloadUrlOfImage = url)
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {
        Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
    }


}