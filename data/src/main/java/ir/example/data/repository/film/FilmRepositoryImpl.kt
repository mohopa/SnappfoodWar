package ir.example.data.repository.film

import ir.example.data.extension.toEither
import ir.example.data.mapper.toFilm
import ir.example.data.repository.film.api.FilmService
import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Film
import ir.mhp.domain.repository.FilmRepository

class FilmRepositoryImpl(
    private val filmService: FilmService
) : FilmRepository {
    override suspend fun getFilm(id: String): Either<Failure, Film.Response> {
        return filmService.getFilm(id).toEither(::toFilm)
    }
}