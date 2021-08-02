package com.peterstaranchuk.heroes_catalog.model

import com.google.gson.annotations.SerializedName

data class ComicsModel(
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String?,
    @SerializedName("isbn") val isbn : String,
    @SerializedName("pageCount") val pageCount : Int,
    @SerializedName("issueNumber") val issueNumber : Int,
    @SerializedName("diamondCode") val diamondCode : String,
    @SerializedName("thumbnail") val thumbnail : Image,
    @SerializedName("images") val images : List<Image>,
)

data class Image(
    @SerializedName("path") val path : String?,
    @SerializedName("extension") val extension : String?,
    ) {

    fun getUrl() : String? {
        return if(path == null || extension == null) {
            null
        } else {
            "$path.$extension"
        }
    }
}