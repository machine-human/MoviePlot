package com.mhuman.movieplot.network.model

import com.google.gson.annotations.SerializedName

data class CastInfoResponse(
    @SerializedName("cast")
    val cast: List<CastInfoList>?,
    @SerializedName("crew")
    val crew: List<CrewInfoList>?,
    @SerializedName("id")
    val id: Int?
)

data class CastInfoList(
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("profile_path")
    val profilePath: String?
)

data class CrewInfoList(
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("job")
    val job: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)