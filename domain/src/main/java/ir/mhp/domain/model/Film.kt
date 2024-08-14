package ir.mhp.domain.model

import ir.mhp.domain.model.film.Movie


sealed class Film {
    data class Request(
        val id: String
    ) : Film()

    data class Response(
        val movie: Movie,
    ) : Film()
}