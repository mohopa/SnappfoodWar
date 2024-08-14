package ir.mhp.domain.model

import ir.mhp.domain.model.planet.HomeWorld


sealed class Planet {
    data class Request(
        val id: String
    ) : Planet()

    data class Response(
        val homeWorld: HomeWorld
    ) : Planet()
}