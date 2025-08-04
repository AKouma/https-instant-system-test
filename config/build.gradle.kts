import com.google.gson.JsonParser
import java.io.FileReader

val secretsJson by lazy {
    val file = rootProject.file("secrets.json")
    JsonParser.parseReader(FileReader(file)).asJsonObject
}

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.alkursi.config"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    flavorDimensions += "env"
    productFlavors {
        create("dev") {
            dimension = "env"
            buildConfigField("String", "NEWS_API_KEY", "\"${secretsJson["dev"].asJsonObject["NEWS_API_KEY"].asString}\"")
            buildConfigField("String", "NEWS_API_HOST", "\"${secretsJson["dev"].asJsonObject["NEWS_API_HOST"].asString}\"")
        }
        create("beta") {
            dimension = "env"
            buildConfigField("String", "NEWS_API_KEY", "\"${secretsJson["beta"].asJsonObject["NEWS_API_KEY"].asString}\"")
            buildConfigField("String", "NEWS_API_HOST", "\"${secretsJson["beta"].asJsonObject["NEWS_API_HOST"].asString}\"")
        }
        create("prod") {
            dimension = "env"
            buildConfigField("String", "NEWS_API_KEY", "\"${secretsJson["prod"].asJsonObject["NEWS_API_KEY"].asString}\"")
            buildConfigField("String", "NEWS_API_HOST", "\"${secretsJson["prod"].asJsonObject["NEWS_API_HOST"].asString}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures { buildConfig = true }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    testImplementation(libs.bundles.koin.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}