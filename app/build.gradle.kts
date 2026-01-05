plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.testdeymervilla.interdata"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.testdeymervilla.interdata"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //Module
    implementation(projects.usecase)
    implementation(projects.presentation)
    //Runtime
    implementation(libs.lifecycle.runtime.ktx)
    //Kotlin
    implementation(libs.core.ktx)
    //Compose
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(platform(libs.compose.bom))
    //Tests Compose
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    debugImplementation(libs.compose.test.manifest)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    //Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    //Hilt
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)
}