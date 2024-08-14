package ir.mhp.snappfoddwar.presentation.film

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mhp.domain.functional.RequestResult
import ir.mhp.domain.model.character.People
import ir.mhp.kit.error.ErrorView
import ir.mhp.kit.loading.LoadingView
import ir.mhp.snappfoddwar.presentation.film.dto.Details
import ir.mhp.snappfoodwar.R
import ir.mhp.utils.extension.getViewModelScope
import org.koin.core.scope.Scope


@Composable
fun FeedDetails(viewModelScope: Scope, people: People) {
    val feedFilmViewModel =
        viewModelScope.getViewModelScope<FeedFilmViewModel>()

    LaunchedEffect(people) {
        feedFilmViewModel?.getData(people)
    }

    val data by feedFilmViewModel?.data?.collectAsState() as State<RequestResult<Details>>

    Column {
        Text(
            text = stringResource(id = R.string.character_detail),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp, 16.dp, 0.dp, 0.dp)
        )

        when (data) {
            RequestResult.Loading -> LoadingView()
            is RequestResult.Error -> ErrorView {
                feedFilmViewModel?.getData(people)
            }

            is RequestResult.Result -> {
                Details(data = (data as RequestResult.Result<Details>).response)
            }

            RequestResult.Idle -> Unit
        }
    }
}


@Composable
fun Details(
    data: Details?
) {
    Surface(
        shape = RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            PeopleDetail(
                population = data?.population.orEmpty(),
                homeWorld = data?.homeWorld.orEmpty()
            )
            FilmItem(film = data?.films)
            SpeciesItem(
                names = data?.speciesName,
                languages = data?.language
            )
        }
    }
}

@Composable
fun PeopleDetail(population: String, homeWorld: String) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Title(text = stringResource(id = R.string.home, homeWorld))
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            Title(text = stringResource(id = R.string.population, population))
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
        }
    }
}

@Composable
fun SpeciesItem(names: List<String?>?, languages: List<String?>?) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            names?.forEach {
                Title(text = stringResource(id = R.string.specie, it.orEmpty()))
                Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            }
            languages?.forEach {
                Title(text = stringResource(id = R.string.language, it.orEmpty()))
                Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            }

        }
    }
}

@Composable
fun FilmItem(film: List<Pair<String?, String?>>?) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            film?.forEach {
                Title(text = stringResource(id = R.string.film, it.first.orEmpty()))
                Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
                Content(text = it.second)
                Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
fun Title(text: String?) {
    Surface(
        color = MaterialTheme.colors.onPrimary
    ) {
        Text(
            text = text.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun Content(text: String?) {
    Surface(color = MaterialTheme.colors.onPrimary, modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = text.orEmpty(),
                modifier = Modifier
                    .padding(top = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
