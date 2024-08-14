package ir.mhp.domain.repository

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacterByName(name: String): Either<Failure, Character.Response>
    suspend fun getCharacterByUrl(url: String): Either<Failure, Character.Response>
}