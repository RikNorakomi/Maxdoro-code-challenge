package com.rikvanvelzen.coding_challenge.model

import com.google.gson.annotations.SerializedName

data class ArtObjectDetails(
    @SerializedName("id") val id: String,
    @SerializedName("objectNumber") val objectNumber: String,

    @SerializedName("longTitle") val title: String?,
    @SerializedName("subTitle") val subTitle: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("physicalMedium") val physicalMedium: String?,
    @SerializedName("materials") val usedMaterials: List<String>?,
    @SerializedName("webImage") val imageDetail: ImageDetails,
    @SerializedName("principalOrFirstMaker") val author: String,
    @SerializedName("dating") val datingInfo: Dating?,
)

data class Dating (
    @SerializedName("presentingDate") val presentingDate: String?
)

