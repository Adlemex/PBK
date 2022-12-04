package com.adlemgames.pbk.models

import com.beust.klaxon.Json

data class Calc(
    @Json(name = "result")
    val result: Any?,
    @Json(name = "steps")
    val steps: List<String>?,
)
