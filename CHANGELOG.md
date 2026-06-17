# Changelog

All notable changes to this project will be documented in this file.

## [1.3.9] - 2026-06-17

### Added

- 93 new `SahhaSensor` types matching the native 1.3.9 SDKs: 39 nutrition, 15 reproductive, and 39 symptom sensors.

### Changed

- Updated the `Sahha` iOS SDK dependency to `1.3.9`.
- Updated the `sahha-android` (`ai.sahha.android:sahha-api`) Android SDK dependency to `1.3.9`.
- Removed the `energy_consumed` sensor from `SahhaSensor` (removed from the native SDK; superseded by `energy_intake`).

### Fixed

- _None._
