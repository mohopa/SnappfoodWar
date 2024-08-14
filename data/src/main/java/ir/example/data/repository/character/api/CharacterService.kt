package ir.example.data.repository.character.api

import ir.example.data.repository.character.api.model.CharactersResponse
import ir.example.data.retrofitutils.GET_PEOPLE_BY_NAME
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CharacterService {

    @GET(GET_PEOPLE_BY_NAME)
    suspend fun getCharacterByName(@Query("search") name: String): Response<CharactersResponse>

    @GET
    suspend fun getCharacter(
        @Url url: String,
    ): Response<CharactersResponse>
}