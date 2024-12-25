plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs") version "2.8.5" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}
