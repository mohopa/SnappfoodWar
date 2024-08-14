package ir.mhp.snappfoddwar.presentation.film.dto


data class Details(
    val speciesName: List<String?>?,
    val language: List<String?>?,
    val homeWorld: String?,
    val population: String?,
    val films: List<Pair<String?, String?>>?,
)
