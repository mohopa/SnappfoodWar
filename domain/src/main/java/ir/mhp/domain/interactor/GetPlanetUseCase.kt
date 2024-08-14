package ir.mhp.domain.interactor

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Planet
import ir.mhp.domain.repository.PlanetRepository

class GetPlanetUseCase(
    private val planetRepository: PlanetRepository
) : UseCase<Planet.Response, Planet.Request>() {

    override suspend fun call(params: Planet.Request): Either<Failure, Planet.Response> {
        return planetRepository.getPlanet(params.id)
    }
}