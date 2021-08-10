package com.rikvanvelzen.md_test.data.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.md_test.data.model.ArtObjectDetails

data class RijksDetailsSearchResponse(
    @SerializedName("artObject") val artObjectDetails: ArtObjectDetails
)
