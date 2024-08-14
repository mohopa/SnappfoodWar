package ir.mhp.snappfoddwar.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ir.mhp.domain.model.character.People
import ir.mhp.snappfoddwar.presentation.character.SearchWithEndlessList
import ir.mhp.snappfoddwar.presentation.film.FeedDetails
import ir.mhp.utils.extension.getParcelableArg
import org.koin.core.scope.Scope

@Composable
fun SetUpNavController(controller: NavHostController, viewModelScope: Scope, gson: Gson) {
    NavHost(navController = controller, startDestination = Screens.FeedCharacter.route) {
        composable(
            route = Screens.FeedCharacter.route
        ) {
            SearchWithEndlessList(
                viewModelScope = viewModelScope,
                onItemClick = { people ->
                    val peopleJson = Uri.encode(gson.toJson(people))
                    controller.navigate("${Screens.FeedDetails.route}/${peopleJson}")
                }
            )
        }
        composable(
            route = "${Screens.FeedDetails.route}/{peopleJson}",
            arguments = listOf(navArgument("peopleJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val peopleJson = backStackEntry.arguments?.getString("peopleJson")
            val people = gson.fromJson(peopleJson, People::class.java)
            people?.let {
                FeedDetails(
                    viewModelScope = viewModelScope,
                    people = it
                )
            }
        }
    }
}