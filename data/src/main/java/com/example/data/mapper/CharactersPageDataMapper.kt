package com.example.data.mapper


import com.example.data.model.characters_list.CharactersResponse
import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import com.example.domain.entity.characters.PageData
import com.example.domain.mapper.DataMapper
import javax.inject.Inject

class CharactersPageDataMapper @Inject constructor() : DataMapper<CharactersResponse, PageData> {
    override fun execute(data: CharactersResponse): PageData {
        return PageData(
            page = data.info.currentPage ?: 1,
            total_pages = data.info.pages,
            results = data.results.map {
                Character(
                    created = it.created,
                    episode = it.episode,
                    gender = it.gender,
                    id = it.id,
                    image = it.image,
                    name = it.name,
                    species = it.species,
                    status = it.status,
                    type = it.type,
                    url = it.url,
                    location = Location(it.location.name, it.location.url)
                )
            })
    }
}