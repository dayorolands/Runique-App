plugins {
    alias(libs.plugins.runique.android.library.compose)
}

android {
    namespace = "com.dayorolands.core.presentation.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystems)
}