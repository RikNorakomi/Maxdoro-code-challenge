package com.rikvanvelzen.md_test.api

import com.google.gson.annotations.SerializedName
import com.rikvanvelzen.md_test.model.ArtObject
import com.rikvanvelzen.md_test.model.ArtObjectDetails

data class RijksDetailsSearchResponse(
    @SerializedName("artObject") val artObjectDetails: ArtObjectDetails
)
