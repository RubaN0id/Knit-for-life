// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

//    id("com.android.application") version "8.9.0" apply false
    alias (libs.plugins.android.application) apply false
    alias (libs.plugins.android.library) apply false
    alias (libs.plugins.kotlin) apply false
    alias (libs.plugins.allOpen) apply false
    alias(libs.plugins.ksp) apply false

}