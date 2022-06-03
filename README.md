# sahha-capacitor

Sahha Cordova Plugin

## Install

```bash
npm install sahha-capacitor
npx cap sync
```

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`authenticate(...)`](#authenticate)
* [`getDemographic()`](#getdemographic)
* [`postDemographic(...)`](#postdemographic)
* [`getSensorStatus(...)`](#getsensorstatus)
* [`enableSensor(...)`](#enablesensor)
* [`postSensorData(...)`](#postsensordata)
* [`analyze(...)`](#analyze)
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


### authenticate(...)

```typescript
authenticate(options: { profileToken: string; refreshToken: string; }) => Promise<{ success: boolean; }>
```

| Param         | Type                                                         |
| ------------- | ------------------------------------------------------------ |
| **`options`** | <code>{ profileToken: string; refreshToken: string; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### getDemographic()

```typescript
getDemographic() => Promise<{ value: string; }>
```

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

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
getSensorStatus(options: { sensor: SahhaSensor; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                                             |
| ------------- | ---------------------------------------------------------------- |
| **`options`** | <code>{ sensor: <a href="#sahhasensor">SahhaSensor</a>; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

--------------------


### enableSensor(...)

```typescript
enableSensor(options: { sensor: SahhaSensor; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                                             |
| ------------- | ---------------------------------------------------------------- |
| **`options`** | <code>{ sensor: <a href="#sahhasensor">SahhaSensor</a>; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

--------------------


### postSensorData(...)

```typescript
postSensorData(options?: { sensors: [SahhaSensor]; } | undefined) => Promise<{ success: boolean; }>
```

| Param         | Type                                     |
| ------------- | ---------------------------------------- |
| **`options`** | <code>{ sensors: [SahhaSensor]; }</code> |

**Returns:** <code>Promise&lt;{ success: boolean; }&gt;</code>

--------------------


### analyze(...)

```typescript
analyze(options?: { startDate: number; endDate: number; } | undefined) => Promise<{ value: string; }>
```

| Param         | Type                                                 |
| ------------- | ---------------------------------------------------- |
| **`options`** | <code>{ startDate: number; endDate: number; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### openAppSettings()

```typescript
openAppSettings() => Promise<void>
```

--------------------


### Interfaces


#### SahhaSettings

| Prop                         | Type                                                          |
| ---------------------------- | ------------------------------------------------------------- |
| **`environment`**            | <code><a href="#sahhaenvironment">SahhaEnvironment</a></code> |
| **`sensors`**                | <code>SahhaSensor[]</code>                                    |
| **`postSensorDataManually`** | <code>boolean</code>                                          |


#### SahhaDemographic

| Prop               | Type                |
| ------------------ | ------------------- |
| **`age`**          | <code>number</code> |
| **`gender`**       | <code>string</code> |
| **`country`**      | <code>string</code> |
| **`birthCountry`** | <code>string</code> |


### Enums


#### SahhaEnvironment

| Members           | Value                      |
| ----------------- | -------------------------- |
| **`development`** | <code>'development'</code> |
| **`production`**  | <code>'production'</code>  |


#### SahhaSensor

| Members         | Value                    |
| --------------- | ------------------------ |
| **`sleep`**     | <code>'sleep'</code>     |
| **`pedometer`** | <code>'pedometer'</code> |
| **`device`**    | <code>'device'</code>    |


#### SahhaSensorStatus

| Members           | Value          |
| ----------------- | -------------- |
| **`pending`**     | <code>0</code> |
| **`unavailable`** | <code>1</code> |
| **`disabled`**    | <code>2</code> |
| **`enabled`**     | <code>3</code> |

</docgen-api>
