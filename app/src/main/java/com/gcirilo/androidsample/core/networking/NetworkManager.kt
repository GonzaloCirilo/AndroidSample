package com.gcirilo.androidsample.core.networking

import kotlinx.coroutines.flow.Flow

interface NetworkManager {

    fun <T>get(relURL: String, clazz: Class<T>): Flow<NetworkResponse<T>>
}