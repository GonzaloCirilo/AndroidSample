package com.gcirilo.androidsample.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gcirilo.androidsample.core.room.AppDatabase
import com.gcirilo.androidsample.core.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabse(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dixi_database",
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    //val workRequest = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    //WorkManager.getInstance(context).enqueue(workRequest)
                }
            })
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Provides
    fun providePostDao(appDatabase: AppDatabase) = appDatabase.postDao()
}