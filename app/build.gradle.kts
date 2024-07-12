import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.zooplsdemo"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.example.zooplsdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val keyProviderFile = project.rootProject.file("keyprovider.properties")
        val properties = Properties()
        properties.load(keyProviderFile.inputStream())

        val baseUrl: String = properties.getProperty("BASE_URL")
        buildConfigField("String", "BASE_URL", baseUrl)

        val apiKey: String = properties.getProperty("API_KEY")
        buildConfigField("String", "API_KEY", apiKey)

        val airPollutionKey: String = properties.getProperty("AIR_POLLUTION_KEY")
        buildConfigField("String", "AIR_POLLUTION_KEY", airPollutionKey)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    kapt {
        correctErrorTypes = true
        generateStubs = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
        dataBinding {
            enable = true
        }
        viewBinding {
            enable = true
        }
    }
}

dependencies {

    // Android Components
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Architecture component navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Recyclerview
    implementation(libs.androidx.recyclerview)

    // ViewModel and LiveData
    implementation(libs.androidx.lifecycle.extensions)

    // Dagger Hilt for dependency injection
    implementation(libs.hilt.android)
    testImplementation(libs.junit.jupiter)
    kapt(libs.hilt.android.compiler)

    // HTTP Login Interceptor
    implementation(libs.logging.interceptor)

    // Retrofit Api Calling Architecture
    implementation(libs.retrofit2.retrofit)

    // JSON to Response Class converter
    implementation(libs.converter.gson)

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    //testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("com.google.truth:truth:1.1.3")
}