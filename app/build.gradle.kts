plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.wowapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apparotech.wowapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //firbase--------
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth:19.4.0")
    implementation ("com.google.android.gms:play-services-auth:18.1.0")

    //firbase database
    implementation ("com.google.firebase:firebase-database:20.0.6")
    implementation ("com.google.firebase:firebase-firestore:24.4.0")
    implementation ("com.google.firebase:firebase-storage:20.1.0")

    //-------------
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //implementation("io.github.joelkanyi:komposecountrycodepicker:1.2.2")
    implementation ("com.hbb20:ccp:2.7.3")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
}