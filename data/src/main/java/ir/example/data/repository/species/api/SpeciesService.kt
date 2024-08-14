package ir.example.data.repository.species.api

import ir.example.data.repository.species.api.model.SpeciesDTO
import ir.example.data.retrofitutils.GET_SPECIES_BY_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpeciesService {

    @GET(GET_SPECIES_BY_ID)
    suspend fun getSpeciesById(@Path("id") id: String): Response<SpeciesDTO>
}