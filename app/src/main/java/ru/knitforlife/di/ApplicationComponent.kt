package ru.knitforlife.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import di.CameraComponent
import ru.knitforlife.database.AppDatabase
import ru.knitforlife.database.di.DbModule
import javax.inject.Qualifier
import javax.inject.Singleton

@Singleton
@Component
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            @AppContext
            context: Context
        ): ApplicationComponent
    }

    @AppContext
    fun provideAppContext(): Context







}

@Qualifier
annotation class AppContext