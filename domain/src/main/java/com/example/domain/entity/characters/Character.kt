package com.example.domain.entity.characters

import kotlinx.serialization.Serializable


@Serializable
data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val location: Location,
)

@Serializable
data class Location(
    val name: String,
    val url: String
)