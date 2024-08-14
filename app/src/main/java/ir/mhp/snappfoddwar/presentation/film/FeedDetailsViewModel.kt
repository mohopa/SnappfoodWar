package ir.mhp.snappfoddwar.presentation.film

import androidx.lifecycle.viewModelScope
import ir.mhp.domain.exception.Failure
import ir.mhp.domain.functional.Either
import ir.mhp.domain.functional.RequestResult
import ir.mhp.domain.interactor.GetFilmUseCase
import ir.mhp.domain.interactor.GetPlanetUseCase
import ir.mhp.domain.interactor.GetSpeciesUseCase
import ir.mhp.domain.model.Film
import ir.mhp.domain.model.Planet
import ir.mhp.domain.model.Species
import ir.mhp.domain.model.character.People
import ir.mhp.domain.model.film.Movie
import ir.mhp.snappfoddwar.presentation.BaseViewModel
import ir.mhp.snappfoddwar.presentation.film.dto.Details
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

abstract class FeedFilmViewModel(
    protected val getFilm: GetFilmUseCase,
    protected val getPlanet: GetPlanetUseCase,
    protected val getSpecies: GetSpeciesUseCase,
) : BaseViewModel(getFilm, getPlanet, getSpecies) {

    abstract val data: StateFlow<RequestResult<Details>>
    abstract fun getData(people: People)
}

class FeedFilmViewModelImpl(
    getFilm: GetFilmUseCase,
    getPlanet: GetPlanetUseCase,
    getSpecies: GetSpeciesUseCase,
) : FeedFilmViewModel(getFilm, getPlanet, getSpecies) {

    private val _data = MutableStateFlow<RequestResult<Details>>(RequestResult.Loading)
    override val data: StateFlow<RequestResult<Details>> = _data


    override fun getData(people: People) {
        viewModelScope.launch {
            _data.value = RequestResult.Loading
            val planetResult = async { getPlanetById(people.homeWorldId.toString()) }

            val speciesResults = people.species?.map { id ->
                async {
                    getSpeciesById(id.toString())
                }
            }

            val filmResults = people.films?.map { id ->
                async {
                    getFilmById(id.toString())
                }
            }

            val planet = planetResult.await()
            val species = speciesResults?.awaitAll() ?: emptyList()
            val films = filmResults?.awaitAll() ?: emptyList()

            if (planet.isLeft ||
                species.firstOrNull { it.isLeft } != null ||
                films.firstOrNull { it.isLeft } != null
            ) {
                _data.value = RequestResult.Error()
            } else {
                val (names, languages) = extractSpecies(species)
                val (population, homeWorldName) = extractPlanet(planet)
                val films = extractFilms(films)
                _data.value = RequestResult.Result(
                    Details(
                        speciesName = names,
                        language = languages,
                        homeWorld = homeWorldName,
                        population = population,
                        films = films
                    )
                )
            }

        }
    }

    private fun extractPlanet(planet: Either<Failure, Planet.Response>): Pair<String?, String?> {
        var population: String? = "0"
        var homeWorldName: String? = ""
        planet.fold({ Unit }) {
            population = it.homeWorld.population
            homeWorldName = it.homeWorld.name
            Unit
        }
        return Pair(population, homeWorldName)
    }

    private fun extractSpecies(species: List<Either<Failure, Species.Response>>): Pair<ArrayList<String?>, ArrayList<String?>> {
        val names = arrayListOf<String?>()
        val languages = arrayListOf<String?>()
        species.map {
            it.fold({ "" }) { res ->
                names.add(res.specie.name)
                languages.add(res.specie.language)
            }
        }
        return Pair(names, languages)
    }

    private fun extractFilms(films: List<Either<Failure, Film.Response>>): ArrayList<Pair<String?, String?>> {
        val data = arrayListOf<Pair<String?, String?>>()
        films.map {
            it.fold({ "" }) { res ->
                data.add(Pair(res.movie.title, res.movie.openingCrawl))
            }
        }
        return data
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getFilmById(id: String) = suspendCancellableCoroutine { continuation ->
        getFilm(
            params = Film.Request(id = id),
            scope = viewModelScope
        ) {
            continuation.resume(it) {
                continuation.cancel()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getPlanetById(id: String) = suspendCancellableCoroutine { continuation ->
        getPlanet(
            params = Planet.Request(id = id),
            scope = viewModelScope
        ) {
            continuation.resume(it) {
                continuation.cancel()
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getSpeciesById(id: String) = suspendCancellableCoroutine { continuation ->
        getSpecies(
            params = Species.Request(id = id),
            scope = viewModelScope
        ) {
            continuation.resume(it) {
                continuation.cancel()
            }
        }
    }
}

