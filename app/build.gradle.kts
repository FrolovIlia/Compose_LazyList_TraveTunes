plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    namespace = "io.travel_tunes"
    compileSdk = 34
    val versionName = "1.0"
    val versionCode = 1

    defaultConfig {
        applicationId = "io.travel_tunes"
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    viewBinding {
        enable = true
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

    val javaVersion = JavaVersion.VERSION_17
    val javaVersionCode = 17

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlin {
        jvmToolchain(javaVersionCode)
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    flavorDimensions.add("city")
    productFlavors {
        create("ivanovo") {
            applicationId = "io.travel_tunes.ivanovo"
            resValue("string", "app_name", "Иваново: Маршруты")
        }
        create("kazan") {
            applicationId = "io.travel_tunes.kazan"
            resValue("string", "app_name", "Казань: Маршруты")
        }
    }


    buildTypes {
        getByName("release") {
            defaultConfig.versionName = versionName
            defaultConfig.versionCode = versionCode
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isDebuggable = false
        }
        getByName("debug") {
            //            firebaseCrashlytics {
            //                // If you don't need crash reporting for your debug build,
            //                // you can speed up your build by disabling mapping file uploading.
            //                mappingFileUploadEnabled false
            //            }
            defaultConfig.versionName = versionName
            defaultConfig.versionCode = versionCode
            isDebuggable = true
        }
    }

    sourceSets {
        getByName("androidTest") {
            // Adds exported schema location as test app assets.
            assets.srcDirs("$projectDir/schemas")
        }
        getByName("ivanovo") {
            java.srcDir("src/ivanovo/java")
            res.srcDir("src/ivanovo/res")
        }
        getByName("kazan") {
            java.srcDir("src/kazan/java")
            res.srcDir("src/kazan/res")
        }
    }
}

dependencies {
    val roomVersion = "2.6.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.7.1")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:compiler:4.16.0")

    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:android-maps-utils:3.8.2")


    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.datastore:datastore-preferences:1.1.0")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-database")
}