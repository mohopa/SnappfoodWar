package ir.mhp.snappfoddwar.presentation.film

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mhp.domain.functional.RequestResult
import ir.mhp.domain.model.character.People
import ir.mhp.domain.model.film.Movie
import ir.mhp.kit.error.ErrorView
import ir.mhp.kit.loading.LoadingView
import ir.mhp.utils.DateUtil
import ir.mhp.utils.extension.getViewModelScope
import ir.mhp.snappfoodwar.R
import org.koin.core.scope.Scope


@Composable
fun FeedDetails(viewModelScope: Scope, people: People?) {
    val feedFilmViewModel =
        viewModelScope.getViewModelScope<FeedFilmViewModel>()

    LaunchedEffect(people) {
        feedFilmViewModel?.getFilmById("1") // Example function to fetch film by ID
    }

    val film by feedFilmViewModel?.film?.collectAsState() as State<RequestResult<Movie>>

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

        when (film) {
            RequestResult.Loading -> LoadingView()
            is RequestResult.Error -> ErrorView {
                //feedFilmViewModel?.getFilmById(filmId)
            }

            is RequestResult.Result -> {
                Details(film = (film as RequestResult.Result<Movie>).response)
            }

            RequestResult.Idle -> Unit
        }

        /*if (tapCount == 0 || tapCount % 10 != 0) {
            FilmsList(films = films)
        }*/

    }
}

/*@OptIn(ExperimentalPagerApi::class)
@Composable
fun FilmsList(films: List<Film>) {
    var filmPosition by remember { mutableStateOf(0) }

    FilmsIndicator(numberOfItems = films.size, currentPage = filmPosition)
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        // Collect from the pager state a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            filmPosition = page
        }
    }

    HorizontalPager(
        count = films.size,
        state = pagerState
    ) {
        FilmItem(film = films[it], Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 0.dp)
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = calculateCurrentOffsetForPage(it)

                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
        )
    }
}*/

/*@Composable
fun FilmsIndicator(numberOfItems: Int, currentPage: Int) {
    if (numberOfItems > 0) {
        Row(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)) {
            for (i in 0 until numberOfItems) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    thickness = 2.dp,
                    color = if (currentPage == i) Color.White else MaterialTheme.colors.primaryVariant
                )
            }
        }
    }

}*/

@Composable
fun Details(
    film: Movie?
) {
    Surface(
        shape = RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            FilmItem(film)
        }
    }
}

@Composable
fun FilmItem(film: Movie?) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleFilm(film = film)
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            film?.let { ContentFilm(film = it) }
        }
    }
}

/*@Composable
fun FilmItem(film: Movie?, modifier: Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(28.dp, 28.dp, 0.dp, 0.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleFilm(film = film)
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            ContentFilm(film = film)
        }
    }
}*/

@Composable
fun TitleFilm(film: Movie?) {
    Surface(
        color = MaterialTheme.colors.onPrimary
    ) {
        Text(
            text = film?.title.orEmpty(),
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
fun ContentFilm(film: Movie) {
    Surface(color = MaterialTheme.colors.onPrimary, modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = DateUtil.displayDate(film.releaseDate),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .alpha(0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )


            Text(
                text = film.openingCrawl.orEmpty(),
                modifier = Modifier
                    .padding(top = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )


            Row {
                Text(
                    text = "director : ",
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = film.director.orEmpty(),
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }


            Row {
                Text(
                    text = "producer : ",
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = film.producer.orEmpty(),
                    modifier = Modifier
                        .padding(top = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .padding(20.dp, 12.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "View characters",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .padding(20.dp, 12.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "View planets",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .padding(20.dp, 12.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "View starships",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .padding(20.dp, 12.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "View vehicles",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .padding(20.dp, 12.dp),
                shape = RoundedCornerShape(50)

            ) {
                Text(
                    text = "View species",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

        }
    }
}
