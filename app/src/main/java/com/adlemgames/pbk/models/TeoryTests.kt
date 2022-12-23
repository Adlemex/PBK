package com.adlemgames.pbk.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeoryTests(
    override val name: String,
    @DrawableRes override val drawable: Int,
    val questions: MutableList<TeoryTest>
) : Parcelable, Teory(name, drawable, Types.MULTI_TEST, "", null) {
}
