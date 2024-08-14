package ir.example.data.repository.character

import ir.example.data.extension.toEither
import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.example.data.mapper.toPeople
import ir.example.data.repository.character.api.CharacterService
import ir.mhp.domain.repository.CharacterRepository
import ir.mhp.domain.model.Character

class CharacterRepositoryImpl(
    private val characterService: CharacterService
) : CharacterRepository {
    override suspend fun getCharacterByName(name: String): Either<Failure, Character.Response> {
        return characterService.getCharacterByName(name).toEither(::toPeople)
    }

    override suspend fun getCharacterByUrl(url: String): Either<Failure, Character.Response> {
        return characterService.getCharacter(url).toEither(::toPeople)
    }
}