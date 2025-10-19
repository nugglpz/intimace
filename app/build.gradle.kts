import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.JavaVersion



plugins {
    // Do not put explicit plugin version strings here in an app module build file.
    // Plugin versions should be controlled via settings.gradle.kts pluginManagement or
    // via the Gradle wrapper. Keep the module plugins simple:
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.intimace"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.intimace"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            // proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    // NOTE: we obtain the compose compiler extension version below from the version catalog
    composeOptions {
        // placeholder — will be set programmatically after we retrieve the catalog
        kotlinCompilerExtensionVersion = "1.6.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

// Read versions from the TOML catalog at configuration time
val libsExtension = extensions.getByType(VersionCatalogsExtension::class.java)
val libs = libsExtension.named("libs")

// Safely read versions (will throw if missing)
val composeCompilerVersion = libs.findVersion("composeCompiler").get().requiredVersion
val coreKtxVersion = libs.findVersion("coreKtx").get().requiredVersion
val lifecycleRuntimeVersion = libs.findVersion("lifecycleRuntimeKtx").get().requiredVersion
val activityComposeVersion = libs.findVersion("activityCompose").get().requiredVersion
val composeBomVersion = libs.findVersion("composeBom").get().requiredVersion
val navigationVersion = libs.findVersion("navigation").get().requiredVersion
val materialIconsVersion = libs.findVersion("materialIcons").get().requiredVersion
val junitVersion = libs.findVersion("junit").get().requiredVersion
val junitExtVersion = libs.findVersion("junitVersion").get().requiredVersion
val espressoVersion = libs.findVersion("espressoCore").get().requiredVersion

// Now apply the compose compiler value (assignment after we've read it)
android.composeOptions.kotlinCompilerExtensionVersion = composeCompilerVersion

dependencies {
    // Core & lifecycle
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion")

    // Activity Compose
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    // Compose BOM to align compose versions
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))

    // Compose artifacts (BOM-managed; no explicit versions)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Material icons (we provide explicit version from the catalog)
    implementation("androidx.compose.material:material-icons-extended:$materialIconsVersion")

    // Navigation compose (explicit version)
    implementation("androidx.navigation:navigation-compose:$navigationVersion")
// Material3
    implementation("androidx.compose.material3:material3")

// Foundation (Compose foundation)
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui-text-google-fonts")

    implementation("commons-validator:commons-validator:1.7")


    // Debug / tooling (BOM-managed where possible)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Tests
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}
