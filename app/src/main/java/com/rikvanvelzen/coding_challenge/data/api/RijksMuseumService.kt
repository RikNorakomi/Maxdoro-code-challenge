package com.rikvanvelzen.coding_challenge.data.api

import com.rikvanvelzen.coding_challenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * For extensive info on the Rijks Data api see:
 * https://data.rijksmuseum.nl/object-metadata/api/
 */
interface RijksMuseumService {

    @GET("collection")
    suspend fun fetchCollectionByInvolvedMaker(
        @Query("involvedMaker") query: String,
        @Query("p") page: Int,
        @Query("ps") resultsPerPage: Int = DEFAULT_RESULT_PER_PAGE,
        @Query("imgonly") onlyResultsWithImage: Boolean = true,
    ): RijksCollectionSearchResponse

    @GET("collection/{objectNumber}")
    suspend fun fetchCollectionDetails(
        @Path("objectNumber") objectNumber: String,
    ): RijksDetailsSearchResponse

    companion object {
        private const val BASE_URL = "https://www.rijksmuseum.nl/api/en/"
        private const val API_KEY = BuildConfig.RIJKS_MUSEUM_API_KEY
        private const val DEFAULT_RESULT_PER_PAGE = 10

        fun create(): RijksMuseumService {

            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BASIC })
                .addInterceptor(ApiKeyInterceptor(API_KEY, "key"))
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RijksMuseumService::class.java)
        }
    }
}

