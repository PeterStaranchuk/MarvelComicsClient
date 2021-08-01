package com.peterstaranchuk.heroes_catalog

import com.google.gson.annotations.SerializedName
import com.peterstaranchuk.heroes_catalog.model.ComicsModel

class ComicsResponse(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: ComicsData,
)

class ComicsData(
    @SerializedName("results") val results: List<ComicsModel>
)