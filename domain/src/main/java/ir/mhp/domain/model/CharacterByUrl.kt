package ir.mhp.domain.model

sealed class CharacterByUrl {
    data class Request(
        val url: String,
    ) : CharacterByUrl()
}