package ir.example.data.di

import ir.example.data.repository.character.CharacterRepositoryImpl
import ir.example.data.repository.film.FilmRepositoryImpl
import ir.example.data.repository.planet.PlanetRepositoryImpl
import ir.example.data.repository.species.SpeciesRepositoryImpl
import ir.mhp.domain.repository.CharacterRepository
import ir.mhp.domain.repository.FilmRepository
import ir.mhp.domain.repository.PlanetRepository
import ir.mhp.domain.repository.SpeciesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(characterService = get()) }
    single<FilmRepository> { FilmRepositoryImpl(filmService = get()) }
    single<PlanetRepository> { PlanetRepositoryImpl(planetService = get()) }
    single<SpeciesRepository> { SpeciesRepositoryImpl(speciesService = get()) }
}
