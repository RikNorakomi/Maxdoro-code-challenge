package com.rikvanvelzen.coding_challenge.data.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.coding_challenge.model.ArtObject

data class RijksCollectionSearchResponse(
    @SerializedName("artObjects") val artObjects: List<ArtObject> = emptyList(),
    @SerializedName("count") val count: Int = 0,
)
