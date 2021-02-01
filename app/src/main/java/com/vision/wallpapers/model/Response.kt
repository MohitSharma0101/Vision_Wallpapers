package com.vision.wallpapers.model

import java.io.Serializable

interface Response : Serializable {
    val idR: Int
    val urlImage: String
    val urlThumb: String
}