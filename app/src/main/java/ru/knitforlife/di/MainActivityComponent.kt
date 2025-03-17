package ru.knitforlife.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import di.CameraComponent
import fragment.CameraFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.knitforlife.MainActivity
import ru.knitforlife.color.di.ColorCollectionComponent
import ru.knitforlife.color.service.ColorApiService
import javax.inject.Qualifier
import javax.inject.Scope

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ColorEventModule::class,SubcomponentsModule::class, ColorApiService::class]
)
interface MainActivityComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @ActivityContext
            @BindsInstance
            activityContext: Context,
            applicationComponent: ApplicationComponent
        ): MainActivityComponent
    }

    @AppContext
    fun provideAppContext(): Context

    @ActivityContext
    fun provideActivityContext(): Context

    fun provideColorEventProducer(): MutableStateFlow<String?>

    fun provideColorEventObserver(): StateFlow<String?>

    fun cameraComponent(): CameraComponent.Factory

    fun colorCollectionComponent(): ColorCollectionComponent.Factory

    fun inject(mainActivity: MainActivity)
}

@Scope
annotation class ActivityScope

@Qualifier
annotation class ActivityContext