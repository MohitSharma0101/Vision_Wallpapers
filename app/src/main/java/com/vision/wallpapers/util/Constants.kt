package com.vision.wallpapers.util

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
        AlphaCategoryResponseItem(1, "White", 1, "https://images2.alphacoders.com/312/31270.jpg"),
        AlphaCategoryResponseItem(2, "Green", 1, "https://images4.alphacoders.com/160/160814.jpg"),
        AlphaCategoryResponseItem(3, "Golden", 1, "https://images6.alphacoders.com/699/699387.jpg"),
        AlphaCategoryResponseItem(4, "Pink", 1, "https://images4.alphacoders.com/272/272706.jpg"),
        AlphaCategoryResponseItem(5, "Blue", 1, "https://images4.alphacoders.com/387/38776.jpg"),
        AlphaCategoryResponseItem(6, "Red", 1, "https://images3.alphacoders.com/238/238685.jpg"),
        AlphaCategoryResponseItem(7, "Black", 1, "https://images4.alphacoders.com/740/74054.jpg")
    )

    val Categories = mutableListOf<AlphaCategoryResponseItem>(

            AlphaCategoryResponseItem(1, "Anime", 1, "https://images3.alphacoders.com/473/47399.png"),
            AlphaCategoryResponseItem(
                    2,
                    "Abstract",
                    1,
                    "https://images.alphacoders.com/724/724313.jpg"
            ),
            AlphaCategoryResponseItem(4, "Game", 1, "https://images6.alphacoders.com/846/846781.jpg"),
            AlphaCategoryResponseItem(5, "Cars", 1, "https://images4.alphacoders.com/222/222694.jpg"),
            AlphaCategoryResponseItem(6, "Sci-Fi", 1, "https://images6.alphacoders.com/948/948308.jpg"),
            AlphaCategoryResponseItem(7, "Nature", 1, "https://images2.alphacoders.com/521/521718.jpg"),

            AlphaCategoryResponseItem(
                    8,
                    "Photography",
                    1,
                    "https://images3.alphacoders.com/853/85305.jpg"
            ),
            AlphaCategoryResponseItem(3, "Horror", 1, "https://images2.alphacoders.com/557/55730.jpg")
    )
}