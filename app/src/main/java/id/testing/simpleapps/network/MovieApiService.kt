package id.testing.simpleapps.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
//        @Query("api_key") apiKey: String = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MzMzYjM1OTUzZGIzMDExNmVmYjk3MTQyOTNkM2I3NyIsIm5iZiI6MTc0OTAyMzMwOS42NjcsInN1YiI6IjY4M2ZmYTRkNDZkNTc0ZmJjNDdmNGVjZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.y05yzeFCg5ApKwBaD4z2G_n9M4i0YZx1N40D0ah6Ixo",//BuildConfig.TMDB_API_KEY,
//        @Query("page") page: Int = 1
    ): MovieResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}