package ir.mhp.domain.interactor

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Film
import ir.mhp.domain.repository.FilmRepository

class GetFilmUseCase(
    private val filmRepository: FilmRepository
) : UseCase<Film.Response, Film.Request>() {

    override suspend fun call(params: Film.Request): Either<Failure, Film.Response> {
        return filmRepository.getFilm(params.id)
    }
}