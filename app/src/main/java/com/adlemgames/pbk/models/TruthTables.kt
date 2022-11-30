package com.adlemgames.pbk.models
import com.beust.klaxon.Json

public data class TruthTables(
    @Json(name = "data")
    val data: List<List<String>>
)