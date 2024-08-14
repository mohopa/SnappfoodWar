package ir.example.data.mapper

import ir.example.data.repository.planet.api.model.PlanetDTO
import ir.mhp.domain.model.Planet
import ir.mhp.domain.model.planet.HomeWorld
import ir.mhp.utils.extension.extractUrlId
import ir.mhp.utils.extension.orNegative
import ir.mhp.utils.extension.safe
import ir.mhp.utils.extension.toDate
import java.util.Date


fun toHomeWorld(planetDTO: PlanetDTO) = Planet.Response(
    homeWorld = HomeWorld(
        climate = planetDTO.climate,
        created = planetDTO.created.safe().toDate() ?: Date(),
        diameter = planetDTO.diameter,
        edited = planetDTO.edited,
        films = planetDTO.films,
        gravity = planetDTO.gravity,
        name = planetDTO.name,
        orbitalPeriod = planetDTO.orbitalPeriod,
        population = planetDTO.population,
        residents = planetDTO.residents,
        rotationPeriod = planetDTO.rotationPeriod,
        surfaceWater = planetDTO.surfaceWater,
        terrain = planetDTO.terrain,
        id = planetDTO.url.safe().extractUrlId().orNegative(),
    )
)