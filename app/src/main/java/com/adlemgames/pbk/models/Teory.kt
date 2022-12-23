package com.adlemgames.pbk.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Teory(
    open val name: String,
    @DrawableRes open val drawable: Int,
    open val type: Types,
    open val path: String,
    open val child: List<Teory>?
) : Parcelable {
    enum class Types {
        FOLDER,
        LIST,
        TEST,
        MULTI_TEST,
    }
}
