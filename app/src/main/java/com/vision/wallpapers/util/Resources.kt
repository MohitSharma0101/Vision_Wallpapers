package com.vision.wallpapers.util

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null) {
    class Success<T>(d: T) : Resources<T>(d)
    class Error<T>(message: String, data: T? = null) : Resources<T>(data, message)
    class Loading<T> : Resources<T>()
}