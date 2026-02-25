# Upgrade Plan: Sahha Capacitor SDK (Capacitor 8)

## 1. Audit: Current vs Target Versions

| Component | Current (Baseline) | Target (Stable) | Status |
| :--- | :--- | :--- | :--- |
| **Capacitor Core** | 8.1.0 | 8.1.0 | **Up to Date** |
| **Capacitor Android** | 8.1.0 | 8.1.0 | **Up to Date** |
| **Capacitor iOS** | 8.1.0 | 8.1.0 | **Up to Date** |
| **Sahha Android SDK** | 1.2.0 | 1.2.0 | **Up to Date** |
| **Sahha iOS SDK** | 1.2.5 | 1.3.5 | **Up to Date** |
| **Android AGP** | 8.7.2 (Plugin) / 8.13.0 (Demo) | 8.7.2 | **Mismatch Found** |
| **Android Compile SDK**| 35 (Plugin) / 36 (Demo) | 35 | **Mismatch Found** |
| **Android Target SDK** | 35 (Plugin) / 36 (Demo) | 35 | **Mismatch Found** |
| **Android Min SDK** | 24 (Plugin) / 26 (Demo) | 26 | **Mismatch Found** |
| **Android Gradle** | 8.11.1 | 8.11.1 | **Confirmed** |
| **Android JDK** | 21.0.9 | 21 | **Confirmed** |
| **iOS Deployment Target**| 15.0 | 15.0 | **Confirmed** |
| **Xcode / Swift** | 26.1 / 6.2 | 26.1 / 6.2 | **Confirmed** |
| **Node.js** | 24.11.1 | 24.x | **Confirmed** |
| **NPM** | 11.6.2 | 11.x | **Confirmed** |

## 2. Expected Breaking Changes

1.  **Capacitor 8 Requirements**: Minimum iOS 15.0 and Android SDK 24 are now hard requirements.
2.  **SDK 35/36 Behavioral Changes**: 
    - Android 15 (SDK 35) introduces strict background service restrictions that may affect sensor data collection.
    - Android 16 (SDK 36) is currently in preview; targeting it in the demo app causes build stability issues with current AGP.
3.  **Java 21 Requirement**: AGP 8.x+ and Capacitor 8 require JDK 21 for builds.
4.  **Gradle Property Assignments**: Using `=` for assignments in `build.gradle` is now mandatory.
5.  **SahhaDemographic Breaking Change**: The `SahhaDemographic` model has been simplified in the latest SDKs (iOS 1.3.5+). It now only supports `gender` and `birthDate`. All other fields (age, country, occupation, etc.) have been removed.

## 3. Step-by-Step Upgrade Checklist

### Phase 1: Core Plugin Stabilization
- [x] Update `package.json` to Capacitor 8.1.0.
- [x] Align `android/build.gradle` with AGP 8.7.2 and Java 21.
- [x] Set `ios.deployment_target` to `15.0` in `.podspec` and `Package.swift`.
- [x] Upgrade Sahha iOS SDK to `1.3.5` in podspec.
- [x] Restore missing Gradle wrappers (`gradlew`) in plugin and example app.
- [x] Fix `SahhaDemographic` model in iOS and Plugin definitions.
- [x] Verify JS/TS build (`npm run build`).

### Phase 2: Example App Recovery
- [x] Align Example App Capacitor dependencies with Plugin (8.1.0).
- [x] **RESTORE `gradlew`**: Standard Gradle wrapper files are moved from temporary init to repository.
- [x] **Fix AGP Mismatch**: Reverted Example App from AGP 8.13.0 (Experimental) to 8.7.2 (Stable).
- [x] **Fix SDK Mismatch**: Reverted Example App from SDK 36 (Preview) to SDK 35 (Stable).
- [x] **Sync Native Projects**: `npx cap sync` successful for both platforms.

### Phase 3: Validation (Smoke Tests)
- [x] **Build Verification**:
    - [x] Android: `./gradlew assembleDebug` success (verified with Java 21).
    - [x] iOS: `xcodebuild` success (Simulator).
- [x] **Runtime Verification**:
    - [x] App launches without crashing (Android/iOS).
    - [x] Sahha SDK initializes and starts permission flow (Android).
    - [ ] `Sahha.authenticate()` successfully retrieves profile token.

## 4. Confirmed Build Blockers

1.  **Resolved: Missing Gradle Wrappers**: Restored `gradlew` files in both plugin and example app.
2.  **Resolved: AGP Version Exception**: Downgraded to 8.7.2.
3.  **Resolved: Java 21 Mismatch**: Explicitly setting `JAVA_HOME` to JDK 21 is required for some terminal environments.
4.  **Pending: SahhaDemographic Version Discrepancy**:
    - Native Android SDK (1.2.0) supports `age`, `country`, etc.
    - Native iOS SDK (1.3.5) **only** supports `gender` and `birthDate`.
    - Current Plugin JS/TS definitions have been reverted to include all fields to support Android, but this will cause iOS compilation errors if not handled conditionally or if the iOS SDK is not downgraded.
5.  **iOS Signing**: Headless builds fail due to missing provisioning profiles. Local Xcode manual signing is used.

## 5. Lean Scope "Done" Definition
Milestone 1 is considered "Done" when:
- All versions are audited and documented.
- Target versions are agreed upon (Capacitor 8, SDK 35, iOS 15).
- Blockers are identified and documented.
- A clear path to a successful build is defined.
