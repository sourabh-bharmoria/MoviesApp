val TMDB_API_KEY: String = project.findProperty("TMDB_API_KEY") as String? ?: ""

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.example.moviesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TMDB_API_KEY", "\"$TMDB_API_KEY\"")

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
        compose = true
        viewBinding = true
        buildConfig = true

    }

    flavorDimensions += "version" //Grouping for flavors

    productFlavors {
        create("dev"){
            dimension = "version"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "MyApp dev")
        }
        create("staging") { //the flavor itself is the name
            dimension = "version"
            applicationIdSuffix = ".staging"
            resValue("string", "app_name", "MyApp Staging")
        }
        create("prod"){
            dimension = "version"
            resValue("string", "app_name", "MyApp prod")

        }
    }

}

secrets {

    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "local.defaults.properties"
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)

    val fragment_version = "1.8.6"

    implementation("androidx.fragment:fragment-ktx:$fragment_version")


    val room_version = "2.7.0"

    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    implementation("androidx.cardview:cardview:1.0.0")

    val paging_version = "3.3.6"

    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.recyclerview)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")


    // Ktor client for Android
    implementation("io.ktor:ktor-client-android:2.3.9")

// Content negotiation + Kotlinx serialization
    implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")

// Kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

// Ktor Logging
    implementation("io.ktor:ktor-client-logging:2.3.9")

//This is for loading image from api into recycleview
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0") // If using annotations


    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.google.android.material:material:1.11.0")

    val work_version = "2.10.1"

    implementation("androidx.work:work-runtime-ktx:$work_version")

    implementation("com.google.android.gms:play-services-location:21.0.1")


    // Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    implementation("com.google.maps.android:android-maps-utils:2.2.3")



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}