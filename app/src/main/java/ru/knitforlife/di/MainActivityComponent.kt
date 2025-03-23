//package ru.knitforlife.di
//
//import android.content.Context
//import dagger.BindsInstance
//import dagger.Component
//import dagger.Provides
//import di.CameraComponent
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import ru.knitforlife.MainActivity
//import ru.knitforlife.color.di.ColorCollectionComponent
//import ru.knitforlife.database.AppDatabase
//import ru.knitforlife.database.dao.ColorDao
//import ru.knitforlife.database.di.DbModule
////import ru.knitforlife.database.di.DbModule
//import ru.knitforlife.network.service.ColorApiService
//
//import javax.inject.Qualifier
//import javax.inject.Scope
//
//@ActivityScope
//@Component(
//    dependencies = [ApplicationComponent::class],
//    modules = [SubcomponentsModule::class, ColorApiService::class
////        ,DbModule::class
//    ]
//)
//interface MainActivityComponent {
//
//    @Component.Factory
//    interface Factory {
//
//        fun create(
//            @ActivityContext
//            @BindsInstance
//            activityContext: Context,
//            applicationComponent: ApplicationComponent
//        ): MainActivityComponent
//    }
//
//    @AppContext
//    fun provideAppContext(): Context
//
//    @ActivityContext
//    fun provideActivityContext(): Context
//
//
//    fun cameraComponent(): CameraComponent.Factory
//
//    fun colorCollectionComponent(): ColorCollectionComponent.Factory
//
//    fun inject(mainActivity: MainActivity)
//}
//
//@Scope
//annotation class ActivityScope
//
//@Qualifier
//annotation class ActivityContext