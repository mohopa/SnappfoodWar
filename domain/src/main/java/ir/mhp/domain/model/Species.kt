package ir.mhp.domain.model

import ir.mhp.domain.model.species.Specie


sealed class Species {
    data class Request(
        val id: String
    ) : Species()

    data class Response(
        val specie: Specie
    ) : Species()
}