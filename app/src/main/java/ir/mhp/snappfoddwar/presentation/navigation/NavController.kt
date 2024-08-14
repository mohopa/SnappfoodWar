package ir.mhp.snappfoddwar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.mhp.domain.model.character.People
import ir.mhp.snappfoddwar.presentation.character.SearchWithEndlessList
import ir.mhp.snappfoddwar.presentation.film.FeedDetails
import ir.mhp.utils.extension.getParcelableArg
import org.koin.core.scope.Scope

@Composable
fun SetUpNavController(controller: NavHostController, viewModelScope: Scope) {
    NavHost(navController = controller, startDestination = Screens.FeedCharacter.route) {
        composable(
            route = Screens.FeedCharacter.route
        ) {
            SearchWithEndlessList(
                viewModelScope = viewModelScope,
                onItemClick = { people ->
                    controller.currentBackStackEntry?.arguments?.apply {
                        putParcelable("people", people)
                    }
                    controller.navigate(Screens.FeedDetails.route)
                }
            )
        }
        composable(
            route = Screens.FeedDetails.route
        ) { backStackEntry ->
            val people = backStackEntry.arguments?.getParcelableArg("people", People::class.java)
            FeedDetails(
                viewModelScope = viewModelScope,
                people = people
            )
        }
    }
}