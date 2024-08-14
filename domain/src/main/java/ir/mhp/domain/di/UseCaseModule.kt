package ir.mhp.domain.di

import ir.mhp.domain.interactor.CharacterUseCase
import ir.mhp.domain.interactor.FindCharacterUseCase
import ir.mhp.domain.interactor.GetFilmUseCase
import ir.mhp.domain.interactor.GetPlanetUseCase
import ir.mhp.domain.interactor.GetSpeciesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetFilmUseCase(filmRepository = get()) }
    factory { GetPlanetUseCase(planetRepository = get()) }
    factory { GetSpeciesUseCase(speciesRepository = get()) }
    factory { FindCharacterUseCase(characterRepository = get()) }
    factory { CharacterUseCase(characterRepository = get()) }
}