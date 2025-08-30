package com.example.domain.entity

class QueryCharacters(
    val page: Int = 1
) {
    fun toQueryMap(): Map<String, String> {
        return mapOf( "page" to "$page")
    }
}