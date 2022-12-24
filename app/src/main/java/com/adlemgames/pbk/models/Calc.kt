package com.adlemgames.pbk.models

import android.os.Parcelable
import com.beust.klaxon.Json
import kotlinx.android.parcel.Parcelize


data class Calc(
    val result: Any?,
    val blocks: List<StepBlock>?,
){
    @Parcelize
    data class StepBlock(
        val title: String,
        val steps: List<String>
    ) : Parcelable
}
