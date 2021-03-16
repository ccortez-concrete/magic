package br.com.concrete.magic.domain.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Root(
    var sets: List<Expansion>
)