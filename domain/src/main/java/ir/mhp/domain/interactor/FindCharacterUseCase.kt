package ir.mhp.domain.interactor

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Character
import ir.mhp.domain.repository.CharacterRepository

class FindCharacterUseCase(
    private val characterRepository: CharacterRepository
) : UseCase<Character.Response, Character.Request>() {

    override suspend fun call(params: Character.Request): Either<Failure, Character.Response> {
        return characterRepository.getCharacterByName(params.name)
    }
}