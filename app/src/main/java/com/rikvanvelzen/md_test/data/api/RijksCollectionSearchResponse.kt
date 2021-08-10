package com.rikvanvelzen.md_test.data.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.md_test.data.model.ArtObject

data class RijksCollectionSearchResponse(
    @SerializedName("artObjects") val artObjects: List<ArtObject> = emptyList(),
    @SerializedName("count") val count: Int = 0,
)
