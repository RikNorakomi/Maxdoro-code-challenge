package com.rikvanvelzen.md_test.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.md_test.model.ArtObject

data class RijksCollectionSearchResponse(
    @SerializedName("artObjects") val artObjects: List<ArtObject> = emptyList(),
    @SerializedName("count") val count: Int = 0,
)
