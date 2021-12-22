package com.masai.sainath.newsapp.di

import android.content.Context
import androidx.room.Room
import com.masai.sainath.newsapp.remote.APIClient
import com.masai.sainath.newsapp.remote.localDb.AppDao
import com.masai.sainath.newsapp.remote.localDb.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val BASE_URL = "https://newsapi.org/"

    @Singleton
    @Provides
    fun provideApiService(): APIClient {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(APIClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context): AppRoomDatabase {
        val builder = Room.databaseBuilder(context, AppRoomDatabase::class.java, "_news_db")
        builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideDataToDb(db: AppRoomDatabase): AppDao {
        return db.getResponseFromDao()
    }
}