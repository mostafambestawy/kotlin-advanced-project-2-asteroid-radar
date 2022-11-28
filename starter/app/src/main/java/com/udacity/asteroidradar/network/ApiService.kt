package com.udacity.asteroidradar.network

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.db.Entities
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.udacity.asteroidradar.db.Entities.AsteroidEntity
private const val BASE_URL = "https://api.nasa.gov/"
private const val TOKEN = "YnSyqz4Rk3xCjBATDnmHPMb4Q82C0R75igrjzkSc"
interface AsteroidServiceInterface {
    /**
     * No Need for start_date and end_date filters as the default behavior is the next seven days as
     * needed
     * for the complexity of request parsing using  moshi,  deferred not used, Call<String> and
     * NetworkUtils.parseAsteroidsJsonResult() function used instead
     * */
    @GET("neo/rest/v1/feed")
    fun getAsteroidList(@Query("api_key") api_key: String = TOKEN): Call<String>

    /**
     * because direct parsing by Moshi library, deferred<PictureOfTheDay> used as a return type
     * */
    @GET("planetary/apod")
    fun getPictureOfTheDay( @Query("api_key") api_key: String = TOKEN) : Deferred<PictureOfDay>




}
/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
/**
 * Call<string>
 * */

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidApi {
    val asteroidServiceInterface:AsteroidServiceInterface by lazy {
        retrofit.create(AsteroidServiceInterface::class.java)
    }
}
