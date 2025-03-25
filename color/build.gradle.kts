plugins {
    alias (libs.plugins.android.library)
    alias (libs.plugins.kotlin)
    alias (libs.plugins.ksp)
    alias (libs.plugins.allOpen)
    alias (libs.plugins.hilt)
}

android {
    namespace = "ru.knitforlife.color"
    compileSdk = 35

    defaultConfig {

        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}

dependencies {
    implementation (project(":database"))
    implementation (project(":network"))
    implementation (project(":core"))




//    implementation(libs.dagger)
//    ksp (libs.dagger.compiler)

    implementation(libs.hilt.android)
    implementation(libs.androidx.vectordrawable)
    ksp (libs.hilt.compiler)


    implementation(libs.core.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
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