plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.healthroutine"
    compileSdk = 36 // 문법 살짝 수정됨 (version = release(36) -> 36)

    defaultConfig {
        applicationId = "com.example.healthroutine"
        minSdk = 24
        targetSdk = 36
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

    // ★ [수정됨] 컴파일 옵션 정리 및 디슈가링 활성화
    compileOptions {
        // 기존에 중복되어 있던 설정을 Java 8로 통일했습니다.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        // 핵심: 디슈가링 활성화 (LocalDate 사용 가능하게 함)
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    // ★ [추가됨] 디슈가링 라이브러리 의존성 추가
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.protolite.well.known.types)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gson)
}