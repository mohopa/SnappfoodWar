package ir.example.data.repository.planet.api

import ir.example.data.repository.planet.api.model.PlanetDTO
import ir.example.data.retrofitutils.GET_PLANET_BY_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlanetService {
    @GET(GET_PLANET_BY_ID)
    suspend fun getPlanetById(@Path("id") id: String): Response<PlanetDTO>
}