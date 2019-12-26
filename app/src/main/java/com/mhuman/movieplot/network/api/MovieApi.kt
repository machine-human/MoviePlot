package com.mhuman.movieplot.network.api

import com.mhuman.movieplot.network.model.CastInfoResponse
import com.mhuman.movieplot.network.model.MovieInfoResponse
import com.mhuman.movieplot.network.model.TrailerInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}/credits")
    fun getCastList(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Single<CastInfoResponse>

    @GET("movie/{movie_id}/videos")
    fun getTrailerKeyList(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Single<TrailerInfoResponse>

    @GET("discover/movie?language=ko-KR&sort_by=popularity.desc&page=1")
    fun getMainMovieList(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Single<MovieInfoResponse>

    @GET("discover/movie?language=ko-KR&sort_by=popularity.desc&page=1")
    fun getGenreMovieList(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("with_keywords") with_keywords: String
    ): Single<MovieInfoResponse>

    @GET("search/movie?language=ko-KR&sort_by=popularity.desc&page=1&include_adult=false")
    fun getSearchMovieList(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Single<MovieInfoResponse>

    companion object {
        val API_BASE_URL = "https://api.themoviedb.org/3/"
        val API_BASE_IMAGE_PATH_URL = "https://image.tmdb.org/t/p/w500"
    }
}
