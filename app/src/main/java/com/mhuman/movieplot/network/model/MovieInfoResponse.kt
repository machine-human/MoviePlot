package com.mhuman.movieplot.network.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class MovieInfoResponse(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<MovieInfoList>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)

@Entity(tableName = "movieInfo", primaryKeys = ["id"])
data class MovieInfoList(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("title")
    val title: String?

/*    @SerializedName("adult")
    val adult: Boolean?,*/

/*    @SerializedName("backdrop_path")
    val backdropPath: String?,*/

/*    @SerializedName("genre_ids")
    val genreIds: List<Int>?,*/

/*    @SerializedName("original_language")
    val original_language: String?,

    @SerializedName("original_title")
    val original_title: String?,*/

/*    @SerializedName("popularity")
    val popularity: Double?,*/

/*    @SerializedName("release_date")
    val release_date: String?,*/

/*    @SerializedName("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    val vote_average: Double?,

    @SerializedName("vote_count")
    val vote_count: Int?*/
)