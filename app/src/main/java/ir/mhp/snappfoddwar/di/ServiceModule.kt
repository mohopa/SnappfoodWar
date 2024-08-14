package ir.mhp.snappfoddwar.di

import ir.example.data.repository.character.api.CharacterService
import ir.example.data.repository.film.api.FilmService
import ir.example.data.repository.planet.api.PlanetService
import ir.example.data.repository.species.api.SpeciesService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { provideCharacterService(retrofit = get()) }
    single { provideFilmService(retrofit = get()) }
    single { providePlanetService(retrofit = get()) }
    single { provideSpeciesService(retrofit = get()) }
}

private fun provideFilmService(retrofit: Retrofit): FilmService {
    return retrofit.create(FilmService::class.java)
}

private fun provideCharacterService(retrofit: Retrofit): CharacterService {
    return retrofit.create(CharacterService::class.java)
}

private fun providePlanetService(retrofit: Retrofit): PlanetService {
    return retrofit.create(PlanetService::class.java)
}

private fun provideSpeciesService(retrofit: Retrofit): SpeciesService {
    return retrofit.create(SpeciesService::class.java)
}