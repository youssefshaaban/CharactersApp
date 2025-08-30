package com.example.data.model.characters_list

data class CharactersResponse(
    val info: Info,
    val results: List<Result>
)

data class Info(
    val count: Int,
    val next: String? = null,
    val pages: Int,
    val prev: String? = null,
    val currentPage: Int?=null
)

data class Location(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)