package ir.mhp.domain.model

import ir.mhp.domain.model.character.People


sealed class Character {
    data class Request(
        val name: String,
    ) : Character()

    data class Response(
        val count: Int?,
        val next: String?,
        val previous: Any?,
        val characters: List<People?>?
    ) : Character()
}