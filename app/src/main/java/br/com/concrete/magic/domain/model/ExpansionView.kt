package br.com.concrete.magic.domain.model

data class ExpansionView (
    val viewType: ExpansionViewType,
    val letter: String = "",
    val expansion: Expansion? = null
)

enum class ExpansionViewType {
    SECTION, ITEM
}

