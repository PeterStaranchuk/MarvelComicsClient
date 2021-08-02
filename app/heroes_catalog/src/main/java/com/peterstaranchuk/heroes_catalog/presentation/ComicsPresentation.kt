package com.peterstaranchuk.heroes_catalog.presentation

data class ComicsPresentation(
    val id: Long,
    val title: String,
    val description: String?,
    val imageUrl: String?
)