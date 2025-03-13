# Sahha Capacitor SDK

The Sahha SDK provides a convenient way for Ionic / Capacitor apps to connect to the Sahha API.

Sahha lets your project seamlessly collect health and lifestyle data from smartphones and wearables
via Apple Health, Google Health Connect, and a variety of other sources.

For more information on Sahha please visit https://sahha.ai.

---

## Docs

The Sahha Docs provide detailed instructions for installation and usage of the Sahha SDK.

[Sahha Docs](https://docs.sahha.ai)

---

## Example

The Sahha Demo App provides a convenient way to try the features of the Sahha SDK.

[Sahha Demo App](https://github.com/sahha-ai/sahha-capacitor/tree/main/example-app)

---

## Health Data Source Integrations

Sahha supports integration with the following health data sources:

- [Apple Health Kit](https://sahha.notion.site/Apple-Health-HealthKit-13cb2f553bbf80c0b117cb662e04c257?pvs=25)
- [Google Fit](https://sahha.notion.site/Google-Fit-131b2f553bbf804a8ee6fef7bc1f4edb?pvs=25)
- [Google Health Connect](https://sahha.notion.site/Health-Connect-Android-13cb2f553bbf806d9d64e79fe9d07d9e?pvs=25)
- [Samsung Health](https://sahha.notion.site/Samsung-Health-d3f76840fad142469f5e724a54c24ead?pvs=25)
- [Fitbit](https://sahha.notion.site/Fitbit-12db2f553bbf809fa93ff01f9acd7330?pvs=25)
- [Garmin Connect](https://sahha.notion.site/Garmin-12db2f553bbf80afb916d04a62e857e6?pvs=25)
- [Polar Flow](https://sahha.notion.site/Polar-12db2f553bbf80c3968eeeab55b484a2?pvs=25)
- [Withings Health Mate](https://sahha.notion.site/Withings-12db2f553bbf80a38d31f80ab083613f?pvs=25)
- [Oura Ring](https://sahha.notion.site/Oura-12db2f553bbf80cf96f2dfd8343b4f06?pvs=25)
- [Whoop](https://sahha.notion.site/WHOOP-12db2f553bbf807192a5c69071e888f4?pvs=25)
- [Strava](https://sahha.notion.site/Strava-12db2f553bbf80c48312c2bf6aa5ac65?pvs=25)
- [Sleep as Android](https://sahha.notion.site/Sleep-as-Android-Smart-alarm-131b2f553bbf802eb7e4dca6baab1049?pvs=25)

& many more! Please visit
our [integrations](https://sahha.notion.site/data-integrations?v=17eb2f553bbf80e0b0b3000c0983ab01)
page for more information.

---

## Install

```bash
npm install sahha-capacitor
npx cap sync
```

### Android

In the `AndroidManifest.xml` file, which can be found in `android` > `app` > `src` > `main`, declare
Google Health Connect data types if required, e.g. sleep and step count.

More data types are available such as heart rate, workout / exercise, please refer to the links
below for more information.

```xml
<!-- Sleep -->
<uses-permission android:name="android.permission.health.READ_SLEEP" />

  <!-- Activity -->
<uses-permission android:name="android.permission.health.READ_STEPS" />
<uses-permission android:name="android.permission.health.READ_FLOORS_CLIMBED" />
```

This is recommended if you'd like to retrieve Health Connect data from other health apps such as
WHOOP, Garmin, Samsung Health etc.

To declare other sensor permissions, please refer
to [this](https://docs.sahha.ai/docs/data-flow/sdk/setup#step-2-review-sensor-permissions) page.

Only include the sensor permissions required by your project, what is declared here will be reviewed
by the Play Store.

You must be able to justify reasons behind requiring the sensor
permissions, [these](https://docs.sahha.ai/docs/data-flow/sdk/app-store-submission/google-play-store#data-type-justifications)
justifications may be used to clearly articulate the reasoning behind your required sensor
permissions.

### Apple iOS

#### Enable HealthKit

- Open your project in Xcode and select your `App Target` in the Project panel.
- Navigate to the `Signing & Capabilities` tab.
- Click the `+` button (or choose `Editor > Add Capability`) to open the Capabilities library.
- Locate and select `HealthKit`; double-click it to add it to your project.

#### Background Delivery

- Select your project in the Project navigator and choose your app’s target.
- In the `Signing & Capabilities` tab, find the HealthKit capability.
- Enable the nested `Background Delivery` option to allow passive health data collection.

#### Add Usage Descriptions

- Select your `App Target` and navigate to the `Info` tab.
- Click the `+` button to add a new key and choose `Privacy - Health Share Usage Description`.
- Provide a clear description, such as: "*This app needs your health info to deliver mood
  predictions*."

For more detailed instructions, refer to
our [setup guide](https://docs.sahha.ai/docs/data-flow/sdk/setup#minimum-requirements).

---

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`isAuthenticated()`](#isauthenticated)
* [`authenticate(...)`](#authenticate)
* [`authenticateToken(...)`](#authenticatetoken)
* [`deauthenticate()`](#deauthenticate)
* [`getProfileToken()`](#getprofiletoken)
* [`getDemographic()`](#getdemographic)
* [`postDemographic(...)`](#postdemographic)
* [`getSensorStatus(...)`](#getsensorstatus)
* [`enableSensors(...)`](#enablesensors)
* [`getScores(...)`](#getscores)
* [`getBiomarkers(...)`](#getbiomarkers)
* [`getStats(...)`](#getstats)
* [`getSamples(...)`](#getsamples)
* [`openAppSettings()`](#openappsettings)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### configure(...)

```typescript
configure(options: { settings: SahhaSettings; }) => Promise<{ success: boolean; }>
```

| Param         | Type                                                                   |
| ------------- | ---------------------------------------------------------------------- |
| **`options`** | <code>{ settings: <a href="#sahhasettings">SahhaSettings</a>; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### isAuthenticated()

```typescript
isAuthenticated() => Promise<{ success: boolean; }>
```

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### authenticate(...)

```typescript
authenticate(options: { appId: string; appSecret: string; externalId: string; }) => Promise<{ success: boolean; }>
```

| Param         | Type                                                                   |
| ------------- | ---------------------------------------------------------------------- |
| **`options`** | <code>{ appId: string; appSecret: string; externalId: string; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### authenticateToken(...)

```typescript
authenticateToken(options: { profileToken: string; refreshToken: string; }) => Promise<{ success: boolean; }>
```

| Param         | Type                                                         |
| ------------- | ------------------------------------------------------------ |
| **`options`** | <code>{ profileToken: string; refreshToken: string; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### deauthenticate()

```typescript
deauthenticate() => Promise<{ success: boolean; }>
```

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### getProfileToken()

```typescript
getProfileToken() => Promise<{ profileToken?: string; }>
```

**Returns:** <code>Promise&lt;{ profileToken?: string; }&gt;</code>

--------------------


### getDemographic()

```typescript
getDemographic() => Promise<{ demographic?: string; }>
```

**Returns:** <code>Promise&lt;{ demographic?: string; }&gt;</code>

--------------------


### postDemographic(...)

```typescript
postDemographic(options: { demographic: SahhaDemographic; }) => Promise<{ success: boolean; }>
```

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code>{ demographic: <a href="#sahhademographic">SahhaDemographic</a>; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### getSensorStatus(...)

```typescript
getSensorStatus(options: { sensors: SahhaSensor[]; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                     |
| ------------- | ---------------------------------------- |
| **`options`** | <code>{ sensors: SahhaSensor[]; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

--------------------


### enableSensors(...)

```typescript
enableSensors(options: { sensors: SahhaSensor[]; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                     |
| ------------- | ---------------------------------------- |
| **`options`** | <code>{ sensors: SahhaSensor[]; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

--------------------


### getScores(...)

```typescript
getScores(options: { types: SahhaScoreType[]; startDateTime: number; endDateTime: number; }) => Promise<{ value: string; }>
```

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code>{ types: SahhaScoreType[]; startDateTime: number; endDateTime: number; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getBiomarkers(...)

```typescript
getBiomarkers(options: { categories: SahhaBiomarkerCategory[]; types: SahhaBiomarkerType[]; startDateTime: number; endDateTime: number; }) => Promise<{ value: string; }>
```

| Param         | Type                                                                                                                            |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ categories: SahhaBiomarkerCategory[]; types: SahhaBiomarkerType[]; startDateTime: number; endDateTime: number; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getStats(...)

```typescript
getStats(options: { sensor: SahhaSensor; startDateTime: number; endDateTime: number; }) => Promise<{ value: string; }>
```

| Param         | Type                                                                                                         |
| ------------- | ------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ sensor: <a href="#sahhasensor">SahhaSensor</a>; startDateTime: number; endDateTime: number; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getSamples(...)

```typescript
getSamples(options: { sensor: SahhaSensor; startDateTime: number; endDateTime: number; }) => Promise<{ value: string; }>
```

| Param         | Type                                                                                                         |
| ------------- | ------------------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ sensor: <a href="#sahhasensor">SahhaSensor</a>; startDateTime: number; endDateTime: number; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### openAppSettings()

```typescript
openAppSettings() => Promise<void>
```

--------------------


### Interfaces


#### SahhaSettings

| Prop                       | Type                                                                       |
| -------------------------- | -------------------------------------------------------------------------- |
| **`environment`**          | <code><a href="#sahhaenvironment">SahhaEnvironment</a></code>              |
| **`sensors`**              | <code>SahhaSensor[]</code>                                                 |
| **`notificationSettings`** | <code>{ icon?: string; title?: string; shortDescription?: string; }</code> |


#### SahhaDemographic

| Prop                    | Type                |
| ----------------------- | ------------------- |
| **`age`**               | <code>number</code> |
| **`gender`**            | <code>string</code> |
| **`country`**           | <code>string</code> |
| **`birthCountry`**      | <code>string</code> |
| **`ethnicity`**         | <code>string</code> |
| **`occupation`**        | <code>string</code> |
| **`industry`**          | <code>string</code> |
| **`incomeRange`**       | <code>string</code> |
| **`education`**         | <code>string</code> |
| **`relationship`**      | <code>string</code> |
| **`locale`**            | <code>string</code> |
| **`livingArrangement`** | <code>string</code> |
| **`birthDate`**         | <code>string</code> |


### Enums


#### SahhaEnvironment

| Members          | Value                     |
| ---------------- | ------------------------- |
| **`sandbox`**    | <code>'sandbox'</code>    |
| **`production`** | <code>'production'</code> |


#### SahhaSensor

| Members                            | Value                                       |
| ---------------------------------- | ------------------------------------------- |
| **`gender`**                       | <code>'gender'</code>                       |
| **`date_of_birth`**                | <code>'date_of_birth'</code>                |
| **`sleep`**                        | <code>'sleep'</code>                        |
| **`steps`**                        | <code>'steps'</code>                        |
| **`floors_climbed`**               | <code>'floors_climbed'</code>               |
| **`heart_rate`**                   | <code>'heart_rate'</code>                   |
| **`resting_heart_rate`**           | <code>'resting_heart_rate'</code>           |
| **`walking_heart_rate_average`**   | <code>'walking_heart_rate_average'</code>   |
| **`heart_rate_variability_sdnn`**  | <code>'heart_rate_variability_sdnn'</code>  |
| **`heart_rate_variability_rmssd`** | <code>'heart_rate_variability_rmssd'</code> |
| **`blood_pressure_systolic`**      | <code>'blood_pressure_systolic'</code>      |
| **`blood_pressure_diastolic`**     | <code>'blood_pressure_diastolic'</code>     |
| **`blood_glucose`**                | <code>'blood_glucose'</code>                |
| **`vo2_max`**                      | <code>'vo2_max'</code>                      |
| **`oxygen_saturation`**            | <code>'oxygen_saturation'</code>            |
| **`respiratory_rate`**             | <code>'respiratory_rate'</code>             |
| **`active_energy_burned`**         | <code>'active_energy_burned'</code>         |
| **`basal_energy_burned`**          | <code>'basal_energy_burned'</code>          |
| **`total_energy_burned`**          | <code>'total_energy_burned'</code>          |
| **`basal_metabolic_rate`**         | <code>'basal_metabolic_rate'</code>         |
| **`time_in_daylight`**             | <code>'time_in_daylight'</code>             |
| **`body_temperature`**             | <code>'body_temperature'</code>             |
| **`basal_body_temperature`**       | <code>'basal_body_temperature'</code>       |
| **`sleeping_wrist_temperature`**   | <code>'sleeping_wrist_temperature'</code>   |
| **`height`**                       | <code>'height'</code>                       |
| **`weight`**                       | <code>'weight'</code>                       |
| **`lean_body_mass`**               | <code>'lean_body_mass'</code>               |
| **`body_mass_index`**              | <code>'body_mass_index'</code>              |
| **`body_fat`**                     | <code>'body_fat'</code>                     |
| **`body_water_mass`**              | <code>'body_water_mass'</code>              |
| **`bone_mass`**                    | <code>'bone_mass'</code>                    |
| **`waist_circumference`**          | <code>'waist_circumference'</code>          |
| **`stand_time`**                   | <code>'stand_time'</code>                   |
| **`move_time`**                    | <code>'move_time'</code>                    |
| **`exercise_time`**                | <code>'exercise_time'</code>                |
| **`activity_summary`**             | <code>'activity_summary'</code>             |
| **`device_lock`**                  | <code>'device_lock'</code>                  |
| **`exercise`**                     | <code>'exercise'</code>                     |


#### SahhaSensorStatus

| Members           | Value          |
| ----------------- | -------------- |
| **`pending`**     | <code>0</code> |
| **`unavailable`** | <code>1</code> |
| **`disabled`**    | <code>2</code> |
| **`enabled`**     | <code>3</code> |


#### SahhaScoreType

| Members                | Value                           |
| ---------------------- | ------------------------------- |
| **`wellbeing`**        | <code>'wellbeing'</code>        |
| **`activity`**         | <code>'activity'</code>         |
| **`sleep`**            | <code>'sleep'</code>            |
| **`readiness`**        | <code>'readiness'</code>        |
| **`mental_wellbeing`** | <code>'mental_wellbeing'</code> |


#### SahhaBiomarkerCategory

| Members              | Value                         |
| -------------------- | ----------------------------- |
| **`activity`**       | <code>'activity'</code>       |
| **`body`**           | <code>'body'</code>           |
| **`characteristic`** | <code>'characteristic'</code> |
| **`reproductive`**   | <code>'reproductive'</code>   |
| **`sleep`**          | <code>'sleep'</code>          |
| **`vitals`**         | <code>'vitals'</code>         |


#### SahhaBiomarkerType

| Members                                | Value                                           |
| -------------------------------------- | ----------------------------------------------- |
| **`steps`**                            | <code>'steps'</code>                            |
| **`floors_climbed`**                   | <code>'floors_climbed'</code>                   |
| **`active_hours`**                     | <code>'active_hours'</code>                     |
| **`active_duration`**                  | <code>'active_duration'</code>                  |
| **`activity_low_intensity_duration`**  | <code>'activity_low_intensity_duration'</code>  |
| **`activity_mid_intensity_duration`**  | <code>'activity_mid_intensity_duration'</code>  |
| **`activity_high_intensity_duration`** | <code>'activity_high_intensity_duration'</code> |
| **`activity_sedentary_duration`**      | <code>'activity_sedentary_duration'</code>      |
| **`active_energy_burned`**             | <code>'active_energy_burned'</code>             |
| **`total_energy_burned`**              | <code>'total_energy_burned'</code>              |
| **`height`**                           | <code>'height'</code>                           |
| **`weight`**                           | <code>'weight'</code>                           |
| **`body_mass_index`**                  | <code>'body_mass_index'</code>                  |
| **`body_fat`**                         | <code>'body_fat'</code>                         |
| **`fat_mass`**                         | <code>'fat_mass'</code>                         |
| **`lean_mass`**                        | <code>'lean_mass'</code>                        |
| **`waist_circumference`**              | <code>'waist_circumference'</code>              |
| **`resting_energy_burned`**            | <code>'resting_energy_burned'</code>            |
| **`age`**                              | <code>'age'</code>                              |
| **`biological_sex`**                   | <code>'biological_sex'</code>                   |
| **`date_of_birth`**                    | <code>'date_of_birth'</code>                    |
| **`menstrual_cycle_length`**           | <code>'menstrual_cycle_length'</code>           |
| **`menstrual_cycle_start_date`**       | <code>'menstrual_cycle_start_date'</code>       |
| **`menstrual_cycle_end_date`**         | <code>'menstrual_cycle_end_date'</code>         |
| **`menstrual_phase`**                  | <code>'menstrual_phase'</code>                  |
| **`menstrual_phase_start_date`**       | <code>'menstrual_phase_start_date'</code>       |
| **`menstrual_phase_end_date`**         | <code>'menstrual_phase_end_date'</code>         |
| **`menstrual_phase_length`**           | <code>'menstrual_phase_length'</code>           |
| **`sleep_start_time`**                 | <code>'sleep_start_time'</code>                 |
| **`sleep_end_time`**                   | <code>'sleep_end_time'</code>                   |
| **`sleep_duration`**                   | <code>'sleep_duration'</code>                   |
| **`sleep_debt`**                       | <code>'sleep_debt'</code>                       |
| **`sleep_interruptions`**              | <code>'sleep_interruptions'</code>              |
| **`sleep_in_bed_duration`**            | <code>'sleep_in_bed_duration'</code>            |
| **`sleep_awake_duration`**             | <code>'sleep_awake_duration'</code>             |
| **`sleep_light_duration`**             | <code>'sleep_light_duration'</code>             |
| **`sleep_rem_duration`**               | <code>'sleep_rem_duration'</code>               |
| **`sleep_deep_duration`**              | <code>'sleep_deep_duration'</code>              |
| **`sleep_regularity`**                 | <code>'sleep_regularity'</code>                 |
| **`sleep_latency`**                    | <code>'sleep_latency'</code>                    |
| **`sleep_efficiency`**                 | <code>'sleep_efficiency'</code>                 |
| **`heart_rate_resting`**               | <code>'heart_rate_resting'</code>               |
| **`heart_rate_sleep`**                 | <code>'heart_rate_sleep'</code>                 |
| **`heart_rate_variability_sdnn`**      | <code>'heart_rate_variability_sdnn'</code>      |
| **`heart_rate_variability_rmssd`**     | <code>'heart_rate_variability_rmssd'</code>     |
| **`respiratory_rate`**                 | <code>'respiratory_rate'</code>                 |
| **`respiratory_rate_sleep`**           | <code>'respiratory_rate_sleep'</code>           |
| **`oxygen_saturation`**                | <code>'oxygen_saturation'</code>                |
| **`oxygen_saturation_sleep`**          | <code>'oxygen_saturation_sleep'</code>          |
| **`vo2_max`**                          | <code>'vo2_max'</code>                          |
| **`blood_glucose`**                    | <code>'blood_glucose'</code>                    |
| **`blood_pressure_systolic`**          | <code>'blood_pressure_systolic'</code>          |
| **`blood_pressure_diastolic`**         | <code>'blood_pressure_diastolic'</code>         |
| **`body_temperature_basal`**           | <code>'body_temperature_basal'</code>           |
| **`skin_temperature_sleep`**           | <code>'skin_temperature_sleep'</code>           |

</docgen-api>

---

Copyright © 2024 Sahha. All rights reserved.
