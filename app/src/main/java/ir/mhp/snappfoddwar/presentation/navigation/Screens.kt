package ir.mhp.snappfoddwar.presentation.navigation

sealed class Screens(val route: String){

    data object FeedCharacter : Screens("FeedCharacter")
    data object FeedDetails : Screens("FeedDetails")
}
