package ir.mhp.snappfoddwar.presentation.character

import androidx.lifecycle.viewModelScope
import ir.mhp.domain.functional.RequestResult
import ir.mhp.domain.interactor.CharacterUseCase
import ir.mhp.domain.interactor.FindCharacterUseCase
import ir.mhp.domain.model.CharacterByUrl
import ir.mhp.domain.model.character.People
import ir.mhp.snappfoddwar.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ir.mhp.domain.model.Character

abstract class CharacterViewModel(
    protected val findCharacter: FindCharacterUseCase,
    protected val characterUseCase: CharacterUseCase,
) : BaseViewModel(findCharacter, characterUseCase) {
    abstract val foundData: StateFlow<RequestResult<List<People?>?>>
    abstract val foundList: List<People?>?
    abstract fun getCharacterByName(name: String? = null)
    abstract fun loadMoreCharacters()
}

class CharacterViewModelImpl(
    findCharacter: FindCharacterUseCase,
    characterUseCase: CharacterUseCase,
) : CharacterViewModel(findCharacter, characterUseCase) {

    private val _foundData = MutableStateFlow<RequestResult<List<People?>?>>(RequestResult.Idle)
    override val foundData: StateFlow<RequestResult<List<People?>?>> = _foundData

    override var foundList: List<People?> = emptyList()
    private var nextSession: String? = null
    private var lastSearched: String? = null
    private var isLoadMore: Boolean = false

    override fun getCharacterByName(name: String?) {
        _foundData.value = RequestResult.Loading
        when {
            name.isNullOrEmpty() ->{
                foundList = emptyList()
                _foundData.value = RequestResult.Result(emptyList())
            }

            lastSearched != name -> {
                findNewCharacter(name)
            }

            !nextSession.isNullOrEmpty() -> {
                continueCharacters()
            }

            else -> {
                foundList = emptyList()
                _foundData.value = RequestResult.Result(emptyList())
            }
        }
    }

    private fun findNewCharacter(name: String?) {
        if (isLoadMore) return
        nextSession = null
        foundList = emptyList()
        lastSearched = name
        findCharacter(
            params = Character.Request(name = name.orEmpty()),
            viewModelScope
        ) {
            it.fold({ error ->
                _foundData.value = RequestResult.Error(error.javaClass.simpleName)
                Any()
            }, { response ->
                nextSession = response.next
                foundList = response.characters ?: emptyList()
                _foundData.value = RequestResult.Result(response.characters)
                Any()
            })
        }
    }

    private fun continueCharacters() {
        isLoadMore = true
        characterUseCase(
            params = CharacterByUrl.Request(url = nextSession.orEmpty()),
            viewModelScope
        ) {
            it.fold({ error ->
                isLoadMore = false
                _foundData.value = RequestResult.Error(error.javaClass.simpleName)
                Any()
            }, { response ->
                isLoadMore = false
                nextSession = response.next

                foundList = foundList + (response.characters ?: emptyList())
                _foundData.value = RequestResult.Result(foundList)
                Any()
            })
        }
    }

    override fun loadMoreCharacters() {
        if (isLoadMore || nextSession.isNullOrEmpty()) return
        getCharacterByName(name = lastSearched)
    }

}