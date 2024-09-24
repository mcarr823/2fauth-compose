plugins {
    id("com.android.application")
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.mcarr.a2fauthcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.mcarr.a2fauthcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val apiDomain = properties.getOrDefault("API_DOMAIN", "") as String
        val apiToken = properties.getOrDefault("API_TOKEN", "") as String
        val apiHttpsVerification = properties.getOrDefault("API_HTTPS_VERIFICATION", "true") as String
        val apiDebugMode = properties.getOrDefault("API_DEBUG_MODE", "false") as String
        val apiStoreSecrets = properties.getOrDefault("API_STORE_SECRETS", "false") as String

        buildConfigField("String", "API_DOMAIN", "\"$apiDomain\"")
        buildConfigField("String", "API_TOKEN", "\"$apiToken\"")
        buildConfigField("Boolean", "API_HTTPS_VERIFICATION", apiHttpsVerification)
        buildConfigField("Boolean", "API_DEBUG_MODE", apiDebugMode)
        buildConfigField("Boolean", "API_STORE_SECRETS", apiStoreSecrets)

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("room.incremental", "true")
            arg("room.expandProjection", "true")
            arg("room.generateKotlin", "true")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlin.onetimepassword)
    testImplementation(libs.junit)
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}