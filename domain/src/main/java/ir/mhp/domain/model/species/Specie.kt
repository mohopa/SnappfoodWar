package ir.mhp.domain.model.species

import java.util.Date


data class Specie(
    val averageHeight: String?,
    val averageLifespan: String?,
    val classification: String?,
    val created: Date?,
    val designation: String?,
    val edited: Date?,
    val eyeColors: String?,
    val films: List<Int?>?,
    val hairColors: String?,
    val homeWorldId: Int?,
    val language: String?,
    val name: String?,
    val people: List<Int?>?,
    val skinColors: String?,
    val id: Int?
)
