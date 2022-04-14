package com.gcirilo.androidsample.core.networking

import com.android.volley.NetworkError

sealed class NetworkResponse<out T> {
    class Success<T>(var data: T? = null): NetworkResponse<T>()
    class Failure<T>(var error: NetworkError): NetworkResponse<T>()
    class Exception<T>(var message: String): NetworkResponse<T>()
}
