package com.vision.wallpapers.util

import com.vision.wallpapers.R
import com.vision.wallpapers.model.alphaCoder.AlphaCategoryResponseItem

object Constants {
    const val HIGH_RATED = "highest_rated"
    const val NEWEST = "newest"
    const val FEATURED = "featured"
    const val POPULAR = "popular"
    const val CATEGORY = "category"
    const val BY_VIEWS = "by_views"
    const val BY_FAVORTIES = "by_favorites"

    val Colors = mutableListOf<AlphaCategoryResponseItem>(
        AlphaCategoryResponseItem(1, "White", R.drawable.white, "https://images2.alphacoders.com/312/31270.jpg"),
        AlphaCategoryResponseItem(2, "Green", R.drawable.green, "https://images4.alphacoders.com/160/160814.jpg"),
        AlphaCategoryResponseItem(3, "Golden", R.drawable.golden, "https://images6.alphacoders.com/699/699387.jpg"),
        AlphaCategoryResponseItem(4, "Pink", R.drawable.pink, "https://images4.alphacoders.com/272/272706.jpg"),
        AlphaCategoryResponseItem(5, "Blue", R.drawable.blue, "https://images4.alphacoders.com/387/38776.jpg"),
        AlphaCategoryResponseItem(6, "Red", R.drawable.red, "https://images3.alphacoders.com/238/238685.jpg"),
        AlphaCategoryResponseItem(7, "Black", R.drawable.black, "https://images4.alphacoders.com/740/74054.jpg")
    )

    val Categories = mutableListOf<AlphaCategoryResponseItem>(

            AlphaCategoryResponseItem(1, "Anime", R.drawable.anime, "https://images3.alphacoders.com/473/47399.png"),
            AlphaCategoryResponseItem(
                    2,
                    "Abstract",
                    R.drawable.resource_abstract,
                    "https://images.alphacoders.com/724/724313.jpg"
            ),
            AlphaCategoryResponseItem(4, "Game", R.drawable.game, "https://images6.alphacoders.com/846/846781.jpg"),
            AlphaCategoryResponseItem(5, "Cars", R.drawable.cars, "https://images4.alphacoders.com/222/222694.jpg"),
            AlphaCategoryResponseItem(6, "Sci-Fi", R.drawable.sci_fi, "https://images6.alphacoders.com/948/948308.jpg"),
            AlphaCategoryResponseItem(7, "Nature", R.drawable.nature, "https://images2.alphacoders.com/521/521718.jpg"),

            AlphaCategoryResponseItem(
                    8,
                    "Photography",
                    R.drawable.photogrphy,
                    "https://images3.alphacoders.com/853/85305.jpg"
            ),
            AlphaCategoryResponseItem(3, "Dark", R.drawable.dark, "https://images2.alphacoders.com/557/55730.jpg")
    )
}