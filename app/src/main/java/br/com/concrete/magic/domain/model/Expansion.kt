package br.com.concrete.magic.domain.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Expansion(
    var code: String,
    var name: String,
    var type: String,
//    var booster: List<String>,
    var releaseDate: String,
    var block: String? = null,
    var onlineOnly: Boolean
)