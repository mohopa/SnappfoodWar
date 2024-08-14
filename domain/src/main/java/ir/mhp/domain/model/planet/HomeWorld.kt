package ir.mhp.domain.model.planet

import java.util.Date


data class HomeWorld(
    val climate: String?,
    val created: Date?,
    val diameter: String?,
    val edited: String?,
    val films: List<String?>?,
    val gravity: String?,
    val name: String?,
    val orbitalPeriod: String?,
    val population: String?,
    val residents: List<String?>?,
    val rotationPeriod: String?,
    val surfaceWater: String?,
    val terrain: String?,
    val id: Int?
)
