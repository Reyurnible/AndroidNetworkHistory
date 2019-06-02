package com.reyurnible.android.networkhistory.repository

import android.text.format.DateUtils
import com.reyurnible.android.networkhistory.entities.Weather
import com.reyurnible.android.networkhistory.repository.WeatherRepository.uri
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * KtorClientでの実装
 * https://ktor.io/clients/http-client.html
 */
class WeatherRepositoryImplKtorClient : WeatherRepository {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        engine {
            connectTimeout = (10 * DateUtils.SECOND_IN_MILLIS).toInt()
            socketTimeout = (10 * DateUtils.SECOND_IN_MILLIS).toInt()
        }
    }

    override fun getWeather(callback: WeatherRepository.RequestCallback) {
        try {
            GlobalScope.launch(Dispatchers.Main) {
                val response = requestGet<Weather>(url = uri.toString()).await()
                callback.success(response)
            }
        } catch (cause: Throwable) {
            callback.error(cause)
        }
    }

    private suspend inline fun <reified T> requestGet(url: String, params: Map<String, Any> = mapOf()): Deferred<T> {
        val request = GlobalScope.async {
            val response = client.get<T>(url)
            client.close()
            return@async response
        }
        return request
    }

    companion object {
        val TAG = WeatherRepositoryImplKtorClient::class.java.simpleName
    }
}
