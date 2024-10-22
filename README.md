# sahha-capacitor

Sahha Capacitor Plugin

## Install

```bash
npm install sahha-capacitor
npx cap sync
```

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
getSensorStatus(options: { sensors: Array<SahhaSensor>; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                     |
| ------------- | ---------------------------------------- |
| **`options`** | <code>{ sensors: SahhaSensor[]; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

--------------------


### enableSensors(...)

```typescript
enableSensors(options: { sensors: Array<SahhaSensor>; }) => Promise<{ status: SahhaSensorStatus; }>
```

| Param         | Type                                     |
| ------------- | ---------------------------------------- |
| **`options`** | <code>{ sensors: SahhaSensor[]; }</code> |

**Returns:** <code>Promise&lt;{ status: <a href="#sahhasensorstatus">SahhaSensorStatus</a>; }&gt;</code>

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


#### Array

| Prop         | Type                | Description                                                                                            |
| ------------ | ------------------- | ------------------------------------------------------------------------------------------------------ |
| **`length`** | <code>number</code> | Gets or sets the length of the array. This is a number one higher than the highest index in the array. |

| Method             | Signature                                                                                                                     | Description                                                                                                                                                                                                                                 |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **toString**       | () =&gt; string                                                                                                               | Returns a string representation of an array.                                                                                                                                                                                                |
| **toLocaleString** | () =&gt; string                                                                                                               | Returns a string representation of an array. The elements are converted to string using their toLocalString methods.                                                                                                                        |
| **pop**            | () =&gt; T \| undefined                                                                                                       | Removes the last element from an array and returns it. If the array is empty, undefined is returned and the array is not modified.                                                                                                          |
| **push**           | (...items: T[]) =&gt; number                                                                                                  | Appends new elements to the end of an array, and returns the new length of the array.                                                                                                                                                       |
| **concat**         | (...items: <a href="#concatarray">ConcatArray</a>&lt;T&gt;[]) =&gt; T[]                                                       | Combines two or more arrays. This method returns a new array without modifying any existing arrays.                                                                                                                                         |
| **concat**         | (...items: (T \| <a href="#concatarray">ConcatArray</a>&lt;T&gt;)[]) =&gt; T[]                                                | Combines two or more arrays. This method returns a new array without modifying any existing arrays.                                                                                                                                         |
| **join**           | (separator?: string \| undefined) =&gt; string                                                                                | Adds all the elements of an array into a string, separated by the specified separator string.                                                                                                                                               |
| **reverse**        | () =&gt; T[]                                                                                                                  | Reverses the elements in an array in place. This method mutates the array and returns a reference to the same array.                                                                                                                        |
| **shift**          | () =&gt; T \| undefined                                                                                                       | Removes the first element from an array and returns it. If the array is empty, undefined is returned and the array is not modified.                                                                                                         |
| **slice**          | (start?: number \| undefined, end?: number \| undefined) =&gt; T[]                                                            | Returns a copy of a section of an array. For both start and end, a negative index can be used to indicate an offset from the end of the array. For example, -2 refers to the second to last element of the array.                           |
| **sort**           | (compareFn?: ((a: T, b: T) =&gt; number) \| undefined) =&gt; this                                                             | Sorts an array in place. This method mutates the array and returns a reference to the same array.                                                                                                                                           |
| **splice**         | (start: number, deleteCount?: number \| undefined) =&gt; T[]                                                                  | Removes elements from an array and, if necessary, inserts new elements in their place, returning the deleted elements.                                                                                                                      |
| **splice**         | (start: number, deleteCount: number, ...items: T[]) =&gt; T[]                                                                 | Removes elements from an array and, if necessary, inserts new elements in their place, returning the deleted elements.                                                                                                                      |
| **unshift**        | (...items: T[]) =&gt; number                                                                                                  | Inserts new elements at the start of an array, and returns the new length of the array.                                                                                                                                                     |
| **indexOf**        | (searchElement: T, fromIndex?: number \| undefined) =&gt; number                                                              | Returns the index of the first occurrence of a value in an array, or -1 if it is not present.                                                                                                                                               |
| **lastIndexOf**    | (searchElement: T, fromIndex?: number \| undefined) =&gt; number                                                              | Returns the index of the last occurrence of a specified value in an array, or -1 if it is not present.                                                                                                                                      |
| **every**          | &lt;S extends T&gt;(predicate: (value: T, index: number, array: T[]) =&gt; value is S, thisArg?: any) =&gt; this is S[]       | Determines whether all the members of an array satisfy the specified test.                                                                                                                                                                  |
| **every**          | (predicate: (value: T, index: number, array: T[]) =&gt; unknown, thisArg?: any) =&gt; boolean                                 | Determines whether all the members of an array satisfy the specified test.                                                                                                                                                                  |
| **some**           | (predicate: (value: T, index: number, array: T[]) =&gt; unknown, thisArg?: any) =&gt; boolean                                 | Determines whether the specified callback function returns true for any element of an array.                                                                                                                                                |
| **forEach**        | (callbackfn: (value: T, index: number, array: T[]) =&gt; void, thisArg?: any) =&gt; void                                      | Performs the specified action for each element in an array.                                                                                                                                                                                 |
| **map**            | &lt;U&gt;(callbackfn: (value: T, index: number, array: T[]) =&gt; U, thisArg?: any) =&gt; U[]                                 | Calls a defined callback function on each element of an array, and returns an array that contains the results.                                                                                                                              |
| **filter**         | &lt;S extends T&gt;(predicate: (value: T, index: number, array: T[]) =&gt; value is S, thisArg?: any) =&gt; S[]               | Returns the elements of an array that meet the condition specified in a callback function.                                                                                                                                                  |
| **filter**         | (predicate: (value: T, index: number, array: T[]) =&gt; unknown, thisArg?: any) =&gt; T[]                                     | Returns the elements of an array that meet the condition specified in a callback function.                                                                                                                                                  |
| **reduce**         | (callbackfn: (previousValue: T, currentValue: T, currentIndex: number, array: T[]) =&gt; T) =&gt; T                           | Calls the specified callback function for all the elements in an array. The return value of the callback function is the accumulated result, and is provided as an argument in the next call to the callback function.                      |
| **reduce**         | (callbackfn: (previousValue: T, currentValue: T, currentIndex: number, array: T[]) =&gt; T, initialValue: T) =&gt; T          |                                                                                                                                                                                                                                             |
| **reduce**         | &lt;U&gt;(callbackfn: (previousValue: U, currentValue: T, currentIndex: number, array: T[]) =&gt; U, initialValue: U) =&gt; U | Calls the specified callback function for all the elements in an array. The return value of the callback function is the accumulated result, and is provided as an argument in the next call to the callback function.                      |
| **reduceRight**    | (callbackfn: (previousValue: T, currentValue: T, currentIndex: number, array: T[]) =&gt; T) =&gt; T                           | Calls the specified callback function for all the elements in an array, in descending order. The return value of the callback function is the accumulated result, and is provided as an argument in the next call to the callback function. |
| **reduceRight**    | (callbackfn: (previousValue: T, currentValue: T, currentIndex: number, array: T[]) =&gt; T, initialValue: T) =&gt; T          |                                                                                                                                                                                                                                             |
| **reduceRight**    | &lt;U&gt;(callbackfn: (previousValue: U, currentValue: T, currentIndex: number, array: T[]) =&gt; U, initialValue: U) =&gt; U | Calls the specified callback function for all the elements in an array, in descending order. The return value of the callback function is the accumulated result, and is provided as an argument in the next call to the callback function. |


#### ConcatArray

| Prop         | Type                |
| ------------ | ------------------- |
| **`length`** | <code>number</code> |

| Method    | Signature                                                          |
| --------- | ------------------------------------------------------------------ |
| **join**  | (separator?: string \| undefined) =&gt; string                     |
| **slice** | (start?: number \| undefined, end?: number \| undefined) =&gt; T[] |


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
| **`step_count`**                   | <code>'step_count'</code>                   |
| **`floor_count`**                  | <code>'floor_count'</code>                  |
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

</docgen-api>
