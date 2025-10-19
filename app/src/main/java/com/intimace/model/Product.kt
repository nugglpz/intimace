package com.intimace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Product(
    @DrawableRes val img: Int,
    val type: String,
    val name: String,
    val location: String,
    val price: Double,
    val quantity: Int,
    val description: String,
    val birthControlHubName: String,
)