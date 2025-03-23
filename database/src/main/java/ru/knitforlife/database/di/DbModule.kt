package ru.knitforlife.database.di

import android.app.AppComponentFactory
import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.knitforlife.database.AppDatabase
import ru.knitforlife.database.dao.ColorDao
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): AppDatabase =  AppDatabase.createInstance(context)

    @Provides
    fun providesColorDao(appDataBase: AppDatabase): ColorDao = appDataBase.colorDao()
}