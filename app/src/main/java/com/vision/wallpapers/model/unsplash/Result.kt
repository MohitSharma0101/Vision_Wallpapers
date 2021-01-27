package com.vision.wallpapers.model.unsplash

import com.vision.wallpapers.model.Response

 data class Result(
         val blur_hash: String,
         val color: String,
         val created_at: String,
         val current_user_collections: List<Any>,
         val description: String,
         val height: Int,
         val id: String,
         val liked_by_user: Boolean,
         val likes: Int,
         val links: Links,
         val urls: Urls,
         val user: User,
         val width: Int,
         val url_image: String,
         val url_thumb: String,
 ):Response{
  override val idR: Int
   get() = id.toInt()
  override val urlImage: String
   get() = urls.full
  override val urlThumb: String
   get() = blur_hash
}
