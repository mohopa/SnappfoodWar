package ir.example.data.mapper

import ir.example.data.repository.character.api.model.CharacterDTO
import ir.example.data.repository.character.api.model.CharactersResponse
import ir.mhp.utils.extension.extractUrlId
import ir.mhp.utils.extension.orNegative
import ir.mhp.utils.extension.safe
import ir.mhp.utils.extension.toDate
import java.util.Date
import ir.mhp.domain.model.Character
import ir.mhp.domain.model.character.People


fun toPeople(response: CharactersResponse) = Character.Response(
    count = response.count,
    next = response.next,
    previous = response.previous,
    characters = response.results?.map { it?.toPeople() }
)

fun CharacterDTO.toPeople() = People(
    birthYear = birthYear,
    created = created.safe().toDate() ?: Date(),
    edited = edited.safe().toDate() ?: Date(),
    eyeColor = eyeColor,
    films = films?.map { it.safe().extractUrlId().orNegative() },
    gender = gender,
    hairColor = hairColor,
    height = height,
    homeWorldId = homeworld.safe().extractUrlId().orNegative(),
    mass = mass,
    name = name,
    skinColor = skinColor,
    species = species?.map { it.safe().extractUrlId().orNegative() },
    starships = starships,
    id = url.safe().extractUrlId().orNegative(),
    vehicles = vehicles,
)