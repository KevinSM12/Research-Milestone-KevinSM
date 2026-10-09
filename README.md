# Research-Milestone-KevinSM — Android app foundation

This Android project foundation is adapted from the reference project:
https://github.com/JosueAR-CS/Research-Milestone-JosueAR

The reference repository has not been modified. This repository is maintained separately.
Please retain this attribution and check with the original author regarding any license or reuse requirements before redistributing the original app-specific assets or feature code.

## Open in Android Studio

1. Extract this ZIP.
2. In Android Studio, choose **Open** and select this folder (the one containing `settings.gradle.kts`).
3. Let Gradle sync. Install any Android SDK components Android Studio requests.
4. Select the `app` run configuration and run on an emulator or Android device.

## Project layout

- `app/src/main/java/com/kevinsm12/researchmilestone/` — Java activities and data classes
- `app/src/main/res/layout/` — XML screen layouts
- `app/src/main/res/drawable/` — drawable resources
- `app/build.gradle.kts` — Android app configuration and dependencies
- `gradle/libs.versions.toml` — plugin/library versions
- `gradle/wrapper/` — Gradle wrapper

The package/application ID was changed to `com.kevinsm12.researchmilestone`. The app uses Java activities and XML layouts; it is not a Compose project.
