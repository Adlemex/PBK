package com.adlemgames.pbk.models

import androidx.annotation.DrawableRes

data class Teory(
    val name: String,
    @DrawableRes val drawable: Int,
    val path: String,
    val child: List<Teory>?
)
