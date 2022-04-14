package com.gcirilo.androidsample.core.di

import com.gcirilo.androidsample.core.repository.PostRepository
import com.gcirilo.androidsample.core.repository.PostRepositoryImpl
import com.gcirilo.androidsample.core.repository.UserRepository
import com.gcirilo.androidsample.core.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getPostSource(repo: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun getUserSource(repo: UserRepositoryImpl): UserRepository
}