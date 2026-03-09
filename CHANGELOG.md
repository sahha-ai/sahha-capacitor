# Changelog

All notable changes to this project will be documented in this file.

## [2.0.0] - 2026-02-26

### Added
- Capacitor 8 Support.
- Native SDK dependencies updated (`Sahha` iOS to `1.3.5`, `sahha-android` Android to `1.2.1`).
- Exported missing `postSensorData` method for Capacitor iOS bridge.
- Full bridging implementation of `getProfileToken` and `deauthenticate` on Android.

### Changed
- Required compileSdkVersion and targetSdkVersion updated to 35 on Android.
- Required Java version updated to 21 on Android.
- Required Kotlin version updated to 1.9.25 on Android.
- Required iOS Deployment Target updated to iOS 15.0.
- Re-architected JSON serialization for Native bridge variables to support deeply nested responses from the Capacitor SDK (`getScores`, `getBiomarkers`, `getStats`, `getSamples`).

### Fixed
- Fixed hanging promises on void-returning functions (`postSensorData`, `openAppSettings`) where they previously stalled JS `await` statements because `call.resolve()` was missing natively.
- Fixed iOS Demographics SDK bridge mapping that previously caused an iOS hard-crash on compile targeting `SahhaDemographic.age` and other removed SDK fields.
