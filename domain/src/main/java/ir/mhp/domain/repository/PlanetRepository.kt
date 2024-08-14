package ir.mhp.domain.repository

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Planet


interface PlanetRepository {
    suspend fun getPlanet(id: String): Either<Failure, Planet.Response>

}