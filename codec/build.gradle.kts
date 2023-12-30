plugins {
    id("multiplatform.tier2")
    id("ink.publishing")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinLibraries.coroutines.core)
                api(projects.structures)
                api(inkLibraries.kimchi.logger)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlinLibraries.kotlin.reflect)
                implementation(kotlinLibraries.kotlin.test.core)
                implementation(kotlinLibraries.kotlin.test.annotations)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlinLibraries.kotlin.reflect)
                implementation(kotlinLibraries.kotlin.test.junit)
                implementation("com.squareup.moshi:moshi-kotlin:1.9.3")
            }
        }
    }
}
