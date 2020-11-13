package com.example.weather.data
import android.telephony.cdma.CdmaCellLocation
import com.example.weather.data.response.CurrentWeather
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY ="0b0068c8cca0bf3b08af6afd13dba365"

//https://api.openweathermap.org/data/2.5/weather?q=London&Lang=en&appid=0b0068c8cca0bf3b08af6afd13dba365

interface ApiWeather {

    @GET ("weather")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query ("lang") languageCode: String = "ru"
    ): Deferred<CurrentWeather>

    companion object {
        operator fun invoke(): ApiWeather {
            val reaquestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(reaquestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiWeather::class.java)
        }
    }
}