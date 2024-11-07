plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.dayorolands.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}