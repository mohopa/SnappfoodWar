package ir.mhp.domain.interactor

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Character
import ir.mhp.domain.model.CharacterByUrl
import ir.mhp.domain.repository.CharacterRepository


class CharacterUseCase(
    private val characterRepository: CharacterRepository
) : UseCase<Character.Response, CharacterByUrl.Request>() {

    override suspend fun call(params: CharacterByUrl.Request): Either<Failure, Character.Response> {
        return characterRepository.getCharacterByUrl(params.url)
    }
}