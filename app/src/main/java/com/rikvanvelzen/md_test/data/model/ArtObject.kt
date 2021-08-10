package com.rikvanvelzen.md_test.data.model

import com.google.gson.annotations.SerializedName

data class ArtObject(
    @SerializedName("id") val id: String,
    @SerializedName("objectNumber") val objectNumber: String,
    @SerializedName("longTitle") val title: String,
    @SerializedName("webImage") val imageDetail: ImageDetails,

    @SerializedName("principalOrFirstMaker") val author: String?,
)

data class ImageDetails (
    @SerializedName("url") val url: String
)



