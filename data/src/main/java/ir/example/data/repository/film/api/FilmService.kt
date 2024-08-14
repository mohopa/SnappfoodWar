package ir.example.data.repository.film.api

import ir.example.data.repository.film.api.model.FilmDto
import ir.example.data.retrofitutils.GET_FILM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmService {
    @GET(GET_FILM)
    suspend fun getFilm(@Path("id") id: String): Response<FilmDto>
}