package ir.mhp.domain.interactor

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Species
import ir.mhp.domain.repository.SpeciesRepository

class GetSpeciesUseCase(
    private val speciesRepository: SpeciesRepository
) : UseCase<Species.Response, Species.Request>() {

    override suspend fun call(params: Species.Request): Either<Failure, Species.Response> {
        return speciesRepository.getSpecie(params.id)
    }
}