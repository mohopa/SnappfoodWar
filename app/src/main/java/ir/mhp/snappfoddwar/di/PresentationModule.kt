package ir.mhp.snappfoddwar.di

import ir.mhp.snappfoddwar.presentation.character.CharacterViewModel
import ir.mhp.snappfoddwar.presentation.character.CharacterViewModelImpl
import ir.mhp.snappfoddwar.presentation.film.FeedFilmViewModel
import ir.mhp.snappfoddwar.presentation.film.FeedFilmViewModelImpl
import ir.mhp.utils.constants.SCOPE_NAME
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    scope(named(SCOPE_NAME)) {
        scoped<FeedFilmViewModel> {
            FeedFilmViewModelImpl(
                getFilm = get(),
                getPlanet = get(),
                getSpecies = get()
            )
        }

        scoped<CharacterViewModel> {
            CharacterViewModelImpl(
                findCharacter = get(),
                characterUseCase = get(),
            )
        }
    }
}