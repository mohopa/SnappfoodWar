package ir.mhp.snappfoddwar.presentation.character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import ir.mhp.utils.extension.getViewModelScope
import ir.mhp.snappfoodwar.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope

@Composable
fun DebouncedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onInputComplete: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }
    var debounceJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onValueChange(newText)

            // Cancel the previous job if it's still running
            debounceJob?.cancel()

            debounceJob = scope.launch {
                delay(500)
                onInputComplete(text)
            }
        },
        label = { Text(stringResource(id = R.string.enter_character_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    )
}

@Composable
fun EndlessList(
    characters: List<People?>,
    listState: LazyListState,
    showLoading: Boolean,
    onLoadMore: () -> Unit,
    onItemClick: (People) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(characters) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
            .collect { lastVisibleItem ->
                if (lastVisibleItem != null && lastVisibleItem.index == characters.size - 1) {
                    coroutineScope.launch {
                        onLoadMore()
                    }
                }
            }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters.size) { position ->
                characters[position]?.let { CharacterItem(it, onItemClick) }
            }
        }
        if (showLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background.copy(alpha = 0.2f))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun CharacterItem(character: People, onClick: (People) -> Unit) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                onClick(character)
            },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TitleText(text = character.name)
            Divider(thickness = 2.dp, color = MaterialTheme.colors.primary)
            TitleText(text = stringResource(R.string.birth_date, character.birthYear.orEmpty()))
        }
    }
}

@Composable
fun TitleText(text: String?) {
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
            textAlign = TextAlign.Start,
            color = Color.White,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun SearchWithEndlessList(
    viewModelScope: Scope,
    onItemClick : (People) -> Unit
) {
    val characterViewModel =
        viewModelScope.getViewModelScope<CharacterViewModel>()
    var query by remember { mutableStateOf("") }
    var characters: List<People?>? by remember { mutableStateOf(null) }
    val result by characterViewModel?.foundData?.collectAsState() as State<RequestResult<List<People?>?>>

    val listState = rememberLazyListState()

    Column {
        DebouncedTextField(
            value = query,
            onValueChange = { query = it },
            onInputComplete = {
                characterViewModel?.getCharacterByName(
                    name = it
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        characters = characterViewModel?.foundList
        EndlessList(
            characters = characters ?: emptyList(),
            listState = listState,
            showLoading = result is RequestResult.Loading,
            onLoadMore = {
                characterViewModel?.loadMoreCharacters()
            },
            onItemClick = onItemClick
        )
    }
}