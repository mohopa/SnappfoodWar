package ir.example.data.mapper

import ir.example.data.repository.species.api.model.SpeciesDTO
import ir.mhp.domain.model.Species
import ir.mhp.domain.model.species.Specie
import ir.mhp.utils.extension.extractUrlId
import ir.mhp.utils.extension.orNegative
import ir.mhp.utils.extension.safe
import ir.mhp.utils.extension.toDate
import java.util.Date


fun toSpecie(speciesDTO: SpeciesDTO) = Species.Response(
    specie = Specie(
        averageHeight = speciesDTO.averageHeight,
        averageLifespan = speciesDTO.averageLifespan,
        classification = speciesDTO.classification,
        created = speciesDTO.created.safe().toDate() ?: Date(),
        designation = speciesDTO.designation,
        edited = speciesDTO.edited.safe().toDate() ?: Date(),
        eyeColors = speciesDTO.eyeColors,
        films = speciesDTO.films?.map { it.safe().extractUrlId().orNegative() },
        hairColors = speciesDTO.hairColors,
        homeWorldId = speciesDTO.homeworld.safe().extractUrlId().orNegative(),
        language = speciesDTO.language,
        name = speciesDTO.name,
        people = speciesDTO.people?.map { it.safe().extractUrlId().orNegative() },
        skinColors = speciesDTO.skinColors,
        id = speciesDTO.url.safe().extractUrlId().orNegative(),
    )
)