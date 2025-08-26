package id.testing.simpleapps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.testing.simpleapps.ui.screens.components.MovieItem
import id.testing.simpleapps.ui.screens.components.RoundedOutlinedTextField
import id.testing.simpleapps.ui.viewmodels.MovieListViewModel
import id.testing.simpleapps.ui.viewmodels.MovieListViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: MovieListViewModel = viewModel(
        factory = MovieListViewModelFactory(context)
    )

    val movies by viewModel.movies.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        // Search Bar
        RoundedOutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            placeholder = "Search movies...",
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
        )

        // Loading indicator
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Movie List
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}