package ir.mhp.snappfoddwar.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import ir.mhp.domain.interactor.UseCase

abstract class BaseViewModel(vararg useCases: UseCase<*, *>) : ViewModel() {
    private val useCases = listOf(*useCases)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
}