package com.mhuman.movieplot.network.model

import com.google.gson.annotations.SerializedName

data class TrailerInfoResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<TrailerKeyList>?
)

data class TrailerKeyList(
    @SerializedName("id")
    val id: String?,
    @SerializedName("iso_3166_1")
    val iso3166_1: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("site")
    val site: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("type")
    val type: String?
)