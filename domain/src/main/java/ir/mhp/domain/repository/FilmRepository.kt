package ir.mhp.domain.repository

import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.model.Film


interface FilmRepository {
    suspend fun getFilm(id: String): Either<Failure, Film.Response>
}