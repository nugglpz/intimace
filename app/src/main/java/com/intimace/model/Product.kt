package com.intimace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Product(
    @DrawableRes val img: Int,
    @StringRes val type: Int,
    @StringRes val name: Int,
    @StringRes val location: Int,
    @StringRes val price: Int,
    @StringRes val description: Int,
    @StringRes val birthControlHubName: Int
)