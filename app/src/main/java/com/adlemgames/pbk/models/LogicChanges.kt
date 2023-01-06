package com.adlemgames.pbk.models
import com.beust.klaxon.Json

public data class LogicChanges(
    @Json(name = "sknf")
    val sknf: String,
    @Json(name = "sdnf")
    val sdnf: String,
    @Json(name = "simplified")
    val simplified: String
)