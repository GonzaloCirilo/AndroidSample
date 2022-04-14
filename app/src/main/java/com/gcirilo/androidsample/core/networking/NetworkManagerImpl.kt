package com.gcirilo.androidsample.core.networking

import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class NetworkManagerImpl @Inject constructor(
    private var networkQueue: RequestQueue
): NetworkManager {

    private suspend fun <T> performJSONRequest(
        method: Int,
        relURL: String? = null,
        clazz: Class<T>
    ): NetworkResponse<T> = suspendCancellableCoroutine { continuation ->
        val request = JsonRequest(
            url = "$BASE_URL$relURL",
            method = method,
            clazz = clazz,
            listener =  {
                continuation.resume(NetworkResponse.Success(it))
            },
            errorListener = { error ->
                val gson = GsonBuilder().create()
                try {
                    val json = String(
                        error.networkResponse?.data ?: ByteArray(0),
                        StandardCharsets.UTF_8
                    )
                    if(json.isNotEmpty()){
                        val networkError = gson.fromJson(json, NetworkError::class.java)
                        continuation.resume(NetworkResponse.Failure(networkError))
                    } else {
                        continuation.resume(NetworkResponse.Exception(error.message.orEmpty()))
                    }
                } catch (e: Exception) {
                    Log.e(NetworkManagerImpl::class.java.simpleName, e.stackTraceToString())
                    continuation.resume(NetworkResponse.Exception(e.message.orEmpty()))
                }
            }
        )

        request.retryPolicy = DefaultRetryPolicy(
            0, // Execute request without delays
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add request to queue.
        networkQueue.add(request)

        continuation.invokeOnCancellation {
            request.cancel()
        }
    }


    override fun <T>get(relURL: String, clazz: Class<T>): Flow<NetworkResponse<T>> {
        return flow {
            val res = performJSONRequest(
                method = Request.Method.GET,
                relURL = relURL,
                clazz = clazz
            )
            emit(res)
        }
    }

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}