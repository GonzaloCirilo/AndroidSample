package com.gcirilo.androidsample.core.di

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.gcirilo.androidsample.core.networking.NetworkManager
import com.gcirilo.androidsample.core.networking.NetworkManagerImpl
import com.gcirilo.androidsample.core.repository.PostRepository
import com.gcirilo.androidsample.core.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a single entrypoint for the network RequestQueue to be used in Network Manager
     */
    @Singleton
    @Provides
    fun provideNetworkQueue(@ApplicationContext context: Context): RequestQueue = Volley.newRequestQueue(context)


}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkManagerModule {
    @Binds
    abstract fun getNetworkManager(repo: NetworkManagerImpl): NetworkManager
}