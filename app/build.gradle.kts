plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.mportog.guidanceprojecttest"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // ViewBinding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android
    implementation(Dependencies.androidx_core)
    implementation(Dependencies.androidx_appcompat)
    implementation(Dependencies.android_material)

    // Test
    testImplementation(Dependencies.junit)

    // AndroidTest
    androidTestImplementation(Dependencies.ext_junit)
    androidTestImplementation(Dependencies.espresso_core)

    // ViewModel
    implementation(Dependencies.viewmodel_ktx)
    // LiveData
    implementation(Dependencies.livedata_ktx)
    // Coroutines e Flow
    implementation(Dependencies.coroutines_android)
    // Koin
    implementation(Dependencies.koin_android)
}
