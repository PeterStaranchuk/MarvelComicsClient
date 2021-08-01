package com.peterstaranchuk.heroes_catalog.model

import com.google.gson.annotations.SerializedName

data class ComicsModel(
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("isbn") val isbn : String,
    @SerializedName("pageCount") val pageCount : Int,
    @SerializedName("issueNumber") val issueNumber : Int,
    @SerializedName("diamondCode") val diamondCode : String,
    @SerializedName("resourceURI") val resourceURI : String,
)