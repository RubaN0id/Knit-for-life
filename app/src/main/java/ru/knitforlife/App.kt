package ru.knitforlife

import android.app.Application
import di.CameraComponent
import di.CameraComponentProvider
import ru.knitforlife.di.ApplicationComponent
import ru.knitforlife.di.DaggerApplicationComponent



class App :Application(){
    lateinit var appComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}