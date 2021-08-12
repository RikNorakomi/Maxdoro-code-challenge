package com.rikvanvelzen.coding_challenge.data.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.coding_challenge.model.ArtObjectDetails

data class RijksDetailsSearchResponse(
    @SerializedName("artObject") val artObjectDetails: ArtObjectDetails
)
