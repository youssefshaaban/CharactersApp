package com.example.domain.entity.characters

data class PageData( val page: Int,
                     val results: List<Character>,
                     val total_pages: Int,
                     )

