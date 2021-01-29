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

    val Categories = mutableListOf<AlphaCategoryResponseItem>(
        AlphaCategoryResponseItem(1, "Anime", 1, "https://images8.alphacoders.com/595/595382.jpg"),
        AlphaCategoryResponseItem(
            2,
            "Abstract",
            1,
            "https://images4.alphacoders.com/438/43818.jpg"
        ),
        AlphaCategoryResponseItem(3, "Dark", 1, "https://images3.alphacoders.com/126/126995.jpg"),
        AlphaCategoryResponseItem(4, "Game", 1, "https://images2.alphacoders.com/861/86152.jpg"),
        AlphaCategoryResponseItem(5, "Cars", 1, "https://images4.alphacoders.com/222/222694.jpg"),
        AlphaCategoryResponseItem(6, "Sci-Fi", 1, "https://images6.alphacoders.com/948/948308.jpg"),
        AlphaCategoryResponseItem(7, "Nature", 1, "https://images2.alphacoders.com/521/521718.jpg"),
        AlphaCategoryResponseItem(
            8,
            "Photography",
            1,
            "https://images3.alphacoders.com/853/85305.jpg"
        )
    )
}