plugins {
    alias (libs.plugins.android.application)
    alias (libs.plugins.kotlin)
    alias (libs.plugins.allOpen)
    alias (libs.plugins.ksp)
    alias (libs.plugins.hilt)
//    id ("com.android.dynamic-feature")
}

android {
    namespace = "ru.knitforlife"
    compileSdk = 35


    defaultConfig {

        applicationId = "ru.knitforlife"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }



    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation (project(":camera"))
    implementation (project(":color"))
    implementation (project(":database"))
    implementation (project(":network"))




    implementation(libs.recyclerview)
//    implementation(libs.dagger)
//    ksp(libs.dagger.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.fragment.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)


    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)


}

allOpen{
    annotation("ru.knitforlife.utils.Open")
}