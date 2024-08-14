package ir.example.data.repository.planet

import ir.example.data.extension.toEither
import ir.example.data.mapper.toHomeWorld
import ir.example.data.repository.planet.api.PlanetService
import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Planet
import ir.mhp.domain.repository.PlanetRepository

class PlanetRepositoryImpl(
    private val planetService: PlanetService
) : PlanetRepository {
    override suspend fun getPlanet(id: String): Either<Failure, Planet.Response> {
        return planetService.getPlanetById(id).toEither(::toHomeWorld)
    }
}