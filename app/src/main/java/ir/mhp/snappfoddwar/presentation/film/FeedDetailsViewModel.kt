package ir.mhp.snappfoddwar.presentation.film

import androidx.lifecycle.viewModelScope
import ir.mhp.domain.functional.RequestResult
import ir.mhp.domain.interactor.GetFilmUseCase
import ir.mhp.domain.interactor.GetPlanetUseCase
import ir.mhp.domain.interactor.GetSpeciesUseCase
import ir.mhp.domain.model.Film
import ir.mhp.domain.model.character.People
import ir.mhp.domain.model.film.Movie
import ir.mhp.snappfoddwar.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class FeedFilmViewModel(
    protected val getFilm: GetFilmUseCase,
    protected val getPlanet: GetPlanetUseCase,
    protected val getSpecies: GetSpeciesUseCase,
) : BaseViewModel(getFilm,getPlanet,getSpecies) {

    abstract val data: StateFlow<RequestResult<Movie>>
    abstract fun getData(people: People)
}

class FeedFilmViewModelImpl(
    getFilm: GetFilmUseCase,
    getPlanet: GetPlanetUseCase,
    getSpecies: GetSpeciesUseCase,
) : FeedFilmViewModel(getFilm, getPlanet, getSpecies) {

    private val _data = MutableStateFlow<RequestResult<Movie>>(RequestResult.Loading)
    override val data: StateFlow<RequestResult<Movie>> = _data



    override fun getData(people: People) {
        viewModelScope.launch {
            _data.value = RequestResult.Loading

        }
    }

    private fun getFilmById(id: String) = getFilm(
        params = Film.Request(id = id),
        viewModelScope
    )
}

