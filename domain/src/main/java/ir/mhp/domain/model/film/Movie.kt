package ir.mhp.domain.model.film

import java.util.Date

data class Movie(
    val id: Int?,
    val title: String?,
    val openingCrawl: String?,
    val director: String?,
    val producer: String?,
    val releaseDate: Date,
    val characters: List<Int>?,
    val planets: List<Int>?,
    val starships: List<String>?,
    val vehicles: List<String>?,
    val species: List<String>?
)
