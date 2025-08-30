package com.example.data.util


import com.example.data.model.characters_list.CharactersResponse
import com.example.data.model.characters_list.Info
import com.example.data.model.characters_list.Result
import com.example.domain.entity.characters.Character
import com.example.domain.entity.characters.Location
import com.google.gson.Gson
import retrofit2.Response

object TestUtil {
    val charactersData: Result = Gson().fromJson(
        "{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Rick Sanchez\",\n" +
                "\"status\": \"Alive\",\n" +
                "\"species\": \"Human\",\n" +
                "\"type\": \"\",\n" +
                "\"gender\": \"Male\",\n" +
                "\"origin\": {\n" +
                "\"name\": \"Earth (C-137)\",\n" +
                "\"url\": \"https://rickandmortyapi.com/api/location/1\"\n" +
                "},\n" +
                "\"location\": {\n" +
                "\"name\": \"Citadel of Ricks\",\n" +
                "\"url\": \"https://rickandmortyapi.com/api/location/3\"\n" +
                "},\n" +
                "\"image\": \"https://rickandmortyapi.com/api/character/avatar/1.jpeg\",\n" +
                "\"episode\": [\n" +
                "\"https://rickandmortyapi.com/api/episode/1\",\n" +
                "\"https://rickandmortyapi.com/api/episode/2\",\n" +
                "\"https://rickandmortyapi.com/api/episode/3\",\n" +
                "\"https://rickandmortyapi.com/api/episode/4\",\n" +
                "\"https://rickandmortyapi.com/api/episode/5\",\n" +
                "\"https://rickandmortyapi.com/api/episode/6\",\n" +
                "\"https://rickandmortyapi.com/api/episode/7\",\n" +
                "\"https://rickandmortyapi.com/api/episode/8\",\n" +
                "\"https://rickandmortyapi.com/api/episode/9\",\n" +
                "\"https://rickandmortyapi.com/api/episode/10\",\n" +
                "\"https://rickandmortyapi.com/api/episode/11\",\n" +
                "\"https://rickandmortyapi.com/api/episode/12\",\n" +
                "\"https://rickandmortyapi.com/api/episode/13\",\n" +
                "\"https://rickandmortyapi.com/api/episode/14\",\n" +
                "\"https://rickandmortyapi.com/api/episode/15\",\n" +
                "\"https://rickandmortyapi.com/api/episode/16\",\n" +
                "\"https://rickandmortyapi.com/api/episode/17\",\n" +
                "\"https://rickandmortyapi.com/api/episode/18\",\n" +
                "\"https://rickandmortyapi.com/api/episode/19\",\n" +
                "\"https://rickandmortyapi.com/api/episode/20\",\n" +
                "\"https://rickandmortyapi.com/api/episode/21\",\n" +
                "\"https://rickandmortyapi.com/api/episode/22\",\n" +
                "\"https://rickandmortyapi.com/api/episode/23\",\n" +
                "\"https://rickandmortyapi.com/api/episode/24\",\n" +
                "\"https://rickandmortyapi.com/api/episode/25\",\n" +
                "\"https://rickandmortyapi.com/api/episode/26\",\n" +
                "\"https://rickandmortyapi.com/api/episode/27\",\n" +
                "\"https://rickandmortyapi.com/api/episode/28\",\n" +
                "\"https://rickandmortyapi.com/api/episode/29\",\n" +
                "\"https://rickandmortyapi.com/api/episode/30\",\n" +
                "\"https://rickandmortyapi.com/api/episode/31\",\n" +
                "\"https://rickandmortyapi.com/api/episode/32\",\n" +
                "\"https://rickandmortyapi.com/api/episode/33\",\n" +
                "\"https://rickandmortyapi.com/api/episode/34\",\n" +
                "\"https://rickandmortyapi.com/api/episode/35\",\n" +
                "\"https://rickandmortyapi.com/api/episode/36\",\n" +
                "\"https://rickandmortyapi.com/api/episode/37\",\n" +
                "\"https://rickandmortyapi.com/api/episode/38\",\n" +
                "\"https://rickandmortyapi.com/api/episode/39\",\n" +
                "\"https://rickandmortyapi.com/api/episode/40\",\n" +
                "\"https://rickandmortyapi.com/api/episode/41\",\n" +
                "\"https://rickandmortyapi.com/api/episode/42\",\n" +
                "\"https://rickandmortyapi.com/api/episode/43\",\n" +
                "\"https://rickandmortyapi.com/api/episode/44\",\n" +
                "\"https://rickandmortyapi.com/api/episode/45\",\n" +
                "\"https://rickandmortyapi.com/api/episode/46\",\n" +
                "\"https://rickandmortyapi.com/api/episode/47\",\n" +
                "\"https://rickandmortyapi.com/api/episode/48\",\n" +
                "\"https://rickandmortyapi.com/api/episode/49\",\n" +
                "\"https://rickandmortyapi.com/api/episode/50\",\n" +
                "\"https://rickandmortyapi.com/api/episode/51\"\n" +
                "],\n" +
                "\"url\": \"https://rickandmortyapi.com/api/character/1\",\n" +
                "\"created\": \"2017-11-04T18:48:46.250Z\"\n" +
                "}", Result::class.java
    )
    val defaultCharactersResponse: CharactersResponse = CharactersResponse(
        info = Info(count = 200, pages = 10), results = listOf(
            charactersData
        )
    )


    fun createMockGetCharactersResponse(): Response<CharactersResponse> {
        // Create a map of currencies for the mock response


        // Create a Response object with your mock data
        return Response.success(defaultCharactersResponse)
    }


    fun createCharacter() = Character(
        id = charactersData.id,
        created = charactersData.created,
        episode = charactersData.episode,
        gender = charactersData.gender,
        image = charactersData.image,
        name = charactersData.name,
        species = charactersData.species,
        status = charactersData.status,
        type = charactersData.type,
        url = charactersData.url,
        location = Location(charactersData.location.name, charactersData.location.url)
    )

}