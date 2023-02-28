package com.adlemgames.pbk.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
open class TeoryVideo(
    override val name: String,
    @DrawableRes override val drawable: Int,
    val url: String,
) : Teory(name, drawable, Teory.Types.VIDEO, url, null), Parcelable
