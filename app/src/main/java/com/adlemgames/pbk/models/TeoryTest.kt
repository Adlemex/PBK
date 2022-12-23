package com.adlemgames.pbk.models

import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeoryTest(
    override val name: String,
    @DrawableRes override val drawable: Int,
    override val type: Types,
    val question: String,
    val answers: MutableList<String>,
    val right_ans: MutableList<Int>,
) : Teory(name, drawable, type, "", null) {
}
