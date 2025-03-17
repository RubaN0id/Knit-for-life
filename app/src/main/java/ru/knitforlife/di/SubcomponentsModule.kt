package ru.knitforlife.di

import dagger.Module
import di.CameraComponent
import ru.knitforlife.color.di.ColorCollectionComponent

@Module(subcomponents = [CameraComponent::class, ColorCollectionComponent::class])
class SubcomponentsModule {
}