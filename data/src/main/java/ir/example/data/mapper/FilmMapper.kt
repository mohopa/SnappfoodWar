package ir.example.data.mapper

import ir.example.data.repository.film.api.model.FilmDto
import ir.mhp.domain.model.Film
import ir.mhp.domain.model.film.Movie
import ir.mhp.utils.extension.extractUrlId
import ir.mhp.utils.extension.orNegative
import ir.mhp.utils.extension.safe
import ir.mhp.utils.extension.toDate
import java.util.Date


fun toFilm(filmDto: FilmDto) = Film.Response(
    Movie(
        id = filmDto.url.safe().extractUrlId().orNegative(),
        title = filmDto.title.safe(),
        openingCrawl = filmDto.openingCrawl.safe(),
        director = filmDto.director.safe(),
        producer = filmDto.producer.safe(),
        releaseDate = filmDto.releaseDate.safe().toDate() ?: Date(),
        characters = filmDto.characters?.map { it.safe().extractUrlId().orNegative() },
        planets = filmDto.planets?.map { it.safe().extractUrlId().orNegative() },
        starships = filmDto.starships ?: arrayListOf(),
        vehicles = filmDto.vehicles ?: arrayListOf(),
        species = filmDto.species ?: arrayListOf(),
    )
)