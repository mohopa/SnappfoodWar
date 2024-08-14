package ir.example.data.repository.species

import ir.example.data.extension.toEither
import ir.example.data.mapper.toSpecie
import ir.example.data.repository.species.api.SpeciesService
import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Species
import ir.mhp.domain.repository.SpeciesRepository

class SpeciesRepositoryImpl(
    private val speciesService: SpeciesService
) : SpeciesRepository {
    override suspend fun getSpecie(id: String): Either<Failure, Species.Response> {
        return speciesService.getSpeciesById(id).toEither(::toSpecie)
    }
}