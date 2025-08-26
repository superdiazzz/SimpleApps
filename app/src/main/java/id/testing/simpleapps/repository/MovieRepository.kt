package id.testing.simpleapps.repository

import android.content.Context
import id.testing.simpleapps.data.Movie
import id.testing.simpleapps.data.MovieDao
import id.testing.simpleapps.data.MovieDatabase
import id.testing.simpleapps.network.MovieApiService
import id.testing.simpleapps.network.NetworkModule
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDao: MovieDao,
    private val apiService: MovieApiService
) {
    fun getMovies(): Flow<List<Movie>> = movieDao.getAllMovies()

    fun searchMovies(query: String): Flow<List<Movie>> = movieDao.searchMovies(query)

    suspend fun getMovieById(id: Int): Movie? = movieDao.getMovieById(id)

    suspend fun refreshMovies() {
        try {
            val response = apiService.getPopularMovies()
            movieDao.clearMovies()
            movieDao.insertMovies(response.results)
        } catch (e: Exception) {
            // Handle error - in production, you might want to show error message
            e.printStackTrace()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(context: Context): MovieRepository {
            return INSTANCE ?: synchronized(this) {
                val database = MovieDatabase.getDatabase(context)
                val apiService = NetworkModule.provideMovieApiService(
                    NetworkModule.provideRetrofit()
                )
                val instance = MovieRepository(database.movieDao(), apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}