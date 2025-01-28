plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.safe.args)
}

android {
    namespace = "com.example.movies"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField(
            "String",
            "API_KEY",
            "\"Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5ZGJiZGY5Y2MyMWViMDIyMWE4MWZmOTI2ZjZmZmUxOSIsIm5iZiI6MTY0ODU5OTkyNC43MTcsInN1YiI6IjYyNDNhMzc0M2UwOWYzMDA0NzAwN2NjOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WbrOqvi0O7Q1PBSlHYzpL5STz6o3HuDQ6B_YxZNicBw\""
        )
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

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.viewmodel)
    implementation(libs.fragment.navigation)
    implementation(libs.ui.navigation)
    implementation(libs.glide)
    implementation(libs.shimmer)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.coroutines)
    testImplementation(libs.mockk)
    testImplementation(libs.arch)
    testImplementation(libs.androidx.espresso.core)
    testImplementation(libs.espresso.intent)
    // UI Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.espresso.intent)
}