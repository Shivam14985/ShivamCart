plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.shivamscart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shivamscart"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation("com.google.firebase:firebase-messaging:24.0.0")
    implementation("androidx.lifecycle:lifecycle-process:2.8.3")
    implementation("com.google.android.gms:play-services-ads:23.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.jpardogo.googleprogressbar:library:1.2.0")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0@aar")
    implementation("com.airbnb.android:lottie:6.4.1")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation("com.hbb20:ccp:2.7.3")
    implementation("com.google.firebase:firebase-messaging:24.0.0")
    implementation("com.google.firebase:firebase-appcheck-safetynet:16.1.2")
    //chatbot dependency
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.github.marlonlom:timeago:4.0.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.firebase:firebase-analytics")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.8.3")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.8.3")
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.facebook.android:audience-network-sdk:6.17.0")
    implementation ("dev.shreyaspatil.EasyUpiPayment:EasyUpiPayment:3.0.3")
}