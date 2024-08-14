package ir.mhp.domain.repository

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Species


interface SpeciesRepository {
    suspend fun getSpecie(id: String): Either<Failure, Species.Response>

}