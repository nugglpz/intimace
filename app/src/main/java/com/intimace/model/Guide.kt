package com.intimace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Guide(
    @DrawableRes val img: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
    ) {
}