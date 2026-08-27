# Changelog

All notable changes to this project will be documented in this file.

## [1.4.0] - 2026-08-27

### Added

- iOS: silent sensor-pipeline failures now post errors to Sahha telemetry, with in-process de-duplication.
- Android: skipped unreadable Health Connect records are now reported to Sahha error telemetry, so affected devices are visible without device logs.

### Changed

- Updated the `Sahha` iOS SDK dependency to `1.4.0`, now sourced from [`sahha-ai/sahha-ios`](https://github.com/sahha-ai/sahha-ios) (the `sahha-ai/sahha-swift` repository is deprecated).
- Updated the `ai.sahha.android:sahha-api` Android SDK dependency to `1.4.0`.
- **Breaking:** `SahhaBiomarkerCategory` now contains exactly the six documented biomarker categories (`activity`, `body`, `engagement`, `nutrition`, `sleep`, `vitals`). Removed `characteristic` and `reproductive`, which never returned biomarker data.
- **Breaking:** renamed `SahhaBiomarkerType.activity_mid_intensity_duration` to `activity_medium_intensity_duration`, matching the server vocabulary — the old name always returned no data.
- **Breaking:** removed the menstrual biomarker types (`menstrual_cycle_length`, `menstrual_cycle_start_date`, `menstrual_cycle_end_date`, `menstrual_phase`, `menstrual_phase_start_date`, `menstrual_phase_end_date`, `menstrual_phase_length`) from `SahhaBiomarkerType`.
- Deprecated `getStats` and `getSamples` — use `getBiomarkers` to read server-processed biomarkers instead. Their results are now labeled with the same `logType` vocabulary used for uploaded data: heart rate types report `heart`, blood pressure/glucose report `blood`, oxygen/VO2 max/respiratory rate report `oxygen`, temperature types report `temperature`, and energy/daylight types report `energy`. `vitals` no longer appears.
- iOS: `deauthenticate` now performs a complete, idempotent purge of the stored session.

### Fixed

- `getSensorStatus` and `enableSensors` now resolve with the documented numeric `SahhaSensorStatus` on both platforms — previously the status arrived as an enum-name string (`"pending"` on iOS, `"PENDING"` on Android) instead of the numeric value declared by the TypeScript enum. `enableSensors` also resolves with the status reported by the native callback instead of making a second status query.
- iOS: `enableSensors` can no longer hang indefinitely without invoking its callback — the native authorization request times out, and concurrent requests are serialized.
- iOS: hardened startup and authentication — `isAuthenticated` reads the persisted session directly from launch, auth-gated calls wait for configuration instead of failing when invoked early, offline launches finish bring-up automatically once connectivity returns, and profile token refresh handles expiry, retry, and rotation races reliably.
- iOS: sensor sets with no HealthKit-backed types (such as `device_lock` alone, or Android-only sensors) no longer make permission requests fail — requesting permissions succeeds as a no-op and sensor status reports correctly through the normal lifecycle.
- iOS: query anchors survive sensor renames so upgrades don't drop or re-ingest data, and anchored HealthKit queries time out cooperatively instead of wedging.
- iOS: corrected the development environment base URL.
- Android: eliminated phantom step spikes caused by step-counter regressions and implausible jumps; reboots are detected reliably, and steps recorded during collection outages are recovered.
- Android: Health Connect reads now step around records the client library cannot parse instead of dropping the surrounding data, and an interrupted read no longer advances the sync marker past unread data.
- Android: `getSamples` for steps no longer returns empty or partial results after background sync has run, and queries no longer interfere with upload de-duplication.
- Android: device-lock events are now stamped with the correct phone form factor.

## [1.3.9] - 2026-06-17

### Added

- 93 new `SahhaSensor` types matching the native 1.3.9 SDKs: 39 nutrition, 15 reproductive, and 39 symptom sensors.

### Changed

- Updated the `Sahha` iOS SDK dependency to `1.3.9`.
- Updated the `sahha-android` (`ai.sahha.android:sahha-api`) Android SDK dependency to `1.3.9`.
- Removed the `energy_consumed` sensor from `SahhaSensor` (removed from the native SDK; superseded by `energy_intake`).

### Fixed

- _None._
