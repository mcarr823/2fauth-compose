// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.1" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    alias(libs.plugins.compose.compiler) apply false
}