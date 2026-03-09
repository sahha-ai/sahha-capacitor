package com.sahha.capacitor;

import ai.sahha.api.Sahha
import ai.sahha.api.biomarkers.SahhaBiomarkerCategory
import ai.sahha.api.biomarkers.SahhaBiomarkerType
import ai.sahha.api.demographic.SahhaDemographic
import ai.sahha.api.notifications.SahhaNotificationConfiguration
import ai.sahha.api.score.SahhaScoreType
import ai.sahha.api.sensors.SahhaSensor
import ai.sahha.api.settings.SahhaEnvironment
import ai.sahha.api.settings.SahhaFramework
import ai.sahha.api.settings.SahhaSettings
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

private const val TAG = "SahhaPlugin"

@CapacitorPlugin(name = "Sahha")
public class SahhaPlugin : Plugin() {

    @PluginMethod
    fun configure(call: PluginCall) {

        val settings: JSObject? = call.getObject("settings")

        if (settings == null) {
            call.reject("Sahha configure settings parameter is missing")
            return
        }

        val environment: String? = settings.getString("environment")
        if (environment == null) {
            call.reject("Sahha configure settings environment parameter is missing")
            return
        }

        Log.d(TAG, "Sahha.configure environment: $environment")
        val sahhaEnvironment: SahhaEnvironment
        try {
            val availableEnvironments = SahhaEnvironment.entries
            sahhaEnvironment = availableEnvironments.find { it.name.equals(environment, ignoreCase = true) }
                ?: SahhaEnvironment.valueOf(environment.uppercase())
            Log.d(TAG, "Sahha.configure resolved to: ${sahhaEnvironment.name}")
        } catch (e: Exception) {
            val available = SahhaEnvironment.entries.joinToString(", ") { it.name }
            Log.e(TAG, "Invalid environment: $environment. Available: $available", e)
            call.reject("Sahha configure settings environment parameter is not valid. Available: $available")
            return
        }

        // Notification config
        var sahhaNotificationConfiguration = SahhaNotificationConfiguration.DEFAULT
        try {
            settings.getJSObject("notificationSettings")?.also { nSettings ->
                val icon = nSettings.getString("icon")
                val title = nSettings.getString("title")
                val shortDescription = nSettings.getString("shortDescription")

                sahhaNotificationConfiguration = SahhaNotificationConfiguration(
                    stringToDrawableResource(
                        context,
                        icon
                    ) ?: 0,
                    title ?: "title",
                    shortDescription ?: "shortDescription",
                )
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha.configure() notification config is not valid")
            return
        }
        // Notification config ends

        val sahhaSettings = SahhaSettings(
            sahhaEnvironment,
            SahhaFramework.CAPACITOR,
            sahhaNotificationConfiguration
        )

        val activity = activity as? ComponentActivity
        if (activity == null) {
            call.reject("Sahha configure app is missing")
        } else {
            Sahha.configure(activity, sahhaSettings) { error, success ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val data = JSObject()
                    data.put("success", success)
                    call.resolve(data)
                }
            }
        }
    }

    @PluginMethod
    fun isAuthenticated(call: PluginCall) {
        val data = JSObject()
        data.put("success", Sahha.isAuthenticated)
        call.resolve(data);
    }

    @PluginMethod
    fun authenticate(call: PluginCall) {

        var appId: String? = call.getString("appId");
        if (appId == null) {
            call.reject("Sahha authenticate appId parameter is missing")
            return
        }

        var appSecret: String? = call.getString("appSecret");
        if (appSecret == null) {
            call.reject("Sahha authenticate appSecret parameter is missing")
            return
        }

        var externalId: String? = call.getString("externalId");
        if (externalId == null) {
            call.reject("Sahha authenticate externalId parameter is missing")
            return
        }

        Log.d(TAG, "Sahha.authenticate starting for appId: $appId, externalId: $externalId")
        Sahha.authenticate(appId, appSecret, externalId)
        { error, success ->
            if (error != null) {
                call.reject(error);
            } else {
                val data = JSObject()
                data.put("success", true)
                call.resolve(data);
            }
        }
    }

    @PluginMethod
    fun deauthenticate(call: PluginCall) {
        Sahha.deauthenticate() { error, success ->
            if (error != null) {
                call.reject(error)
            } else {
                val data = JSObject()
                data.put("success", success)
                call.resolve(data)
            }
        }
    }

    @PluginMethod
    fun getProfileToken(call: PluginCall) {
        val profileToken = Sahha.profileToken
        if (profileToken != null) {
            val data = JSObject()
            data.put("profileToken", profileToken)
            call.resolve(data)
        } else {
            call.reject("No profile token found")
        }
    }

    @PluginMethod
    fun authenticateToken(call: PluginCall) {

        var profileToken: String? = call.getString("profileToken");
        if (profileToken == null) {
            call.reject("Sahha authenticate profileToken parameter is missing")
            return
        }

        var refreshToken: String? = call.getString("refreshToken");
        if (refreshToken == null) {
            call.reject("Sahha authenticate refreshToken parameter is missing")
            return
        }

        Sahha.authenticate(profileToken, refreshToken)
        { error, success ->
            if (error != null) {
                call.reject(error);
            } else {
                val data = JSObject()
                data.put("success", success)
                call.resolve(data);
            }
        }
    }

    @PluginMethod
    fun getDemographic(call: PluginCall) {

        Sahha.getDemographic() { error, demographic ->
            if (error != null) {
                call.reject(error)
            } else if (demographic != null) {

                // Need to handle possible null values
                val gson = GsonBuilder().serializeNulls().create()

                val demographicJson: String = gson.toJson(demographic)
                Log.d("Sahha", demographicJson)
                val data = JSObject()
                data.put("demographic", demographicJson)
                call.resolve(data)
            } else {
                call.reject("Sahha demographic not available")
            }
        }
    }

    @PluginMethod
    fun postDemographic(call: PluginCall) {

        var demographic: JSObject? = call.getObject("demographic")

        if (demographic == null) {
            call.reject("Sahha demographic parameter is missing")
            return
        }

        val age: Int? = demographic.optInt("age")
        val gender: String? = demographic.optString("gender")
        val birthDate: String? = demographic.optString("birthDate")

        var sahhaDemographic = SahhaDemographic(
            age = if (age == 0) null else age,
            gender = gender?.ifEmpty { null },
            birthDate = birthDate?.ifEmpty { null },
        )
        Log.d("Sahha", sahhaDemographic.toString())

        Sahha.postDemographic(sahhaDemographic) { error, success ->
            if (error != null) {
                call.reject(error)
            } else {
                val data = JSObject()
                data.put("success", success)
                call.resolve(data)
            }
        }
    }


private fun parseSensors(call: PluginCall, methodName: String): MutableSet<SahhaSensor>? {
    val sensors: JSArray? = call.getArray("sensors")

    if (sensors == null) {
        call.reject("Sahha $methodName sensors parameter is missing")
        return null
    }

    val sahhaSensors = mutableSetOf<SahhaSensor>()
    try {
        for (i in 0 until sensors.length()) {
            val sensor = sensors.getString(i)
            val sahhaSensor = SahhaSensor.valueOf(sensor.uppercase())
            sahhaSensors.add(sahhaSensor)
        }
    } catch (e: IllegalArgumentException) {
        call.reject("Sahha sensor parameter is not valid")
        return null
    }

    return sahhaSensors
}

private fun resolveSensorStatus(call: PluginCall, sahhaSensors: Set<SahhaSensor>) {
    Sahha.getSensorStatus(sahhaSensors) { error, sensorStatus ->
        if (error != null) {
            call.reject(error)
        } else {
            val data = JSObject()
            data.put("status", sensorStatus)
            call.resolve(data)
        }
    }
}


  @PluginMethod
fun getSensorStatus(call: PluginCall) {
    val sahhaSensors = parseSensors(call, "getSensorStatus") ?: return
    resolveSensorStatus(call, sahhaSensors)
}

@PluginMethod
fun enableSensors(call: PluginCall) {
    val sahhaSensors = parseSensors(call, "enableSensors") ?: return

    Sahha.enableSensors(sahhaSensors) { error, _ ->
        if (error != null) {
            call.reject(error)
        } else {
            resolveSensorStatus(call, sahhaSensors)
        }
    }
}

    @PluginMethod
    fun getScores(call: PluginCall) {
        val types: JSArray? = call.getArray("types")
        val startDateEpochMilli: Long? = call.getLong("startDateTime")
        println("startDateTime provided: $startDateEpochMilli")
        val endDateEpochMilli: Long? = call.getLong("endDateTime")
        println("endDateTime provided: $endDateEpochMilli")

        if (types == null) {
            call.reject("Sahha getScores types parameter is missing")
            return
        }

        if (startDateEpochMilli == null) {
            call.reject("Sahha getScores startDateTime parameter is missing")
            return
        }

        if (endDateEpochMilli == null) {
            call.reject("Sahha getScores endDateTime parameter is missing")
            return
        }

        val sahhaScoreTypes: MutableSet<SahhaScoreType> = mutableSetOf<SahhaScoreType>()
        try {
            for (i in 0 until types.length()) {
                val type: String = types.getString(i)
                val scoreType = SahhaScoreType.valueOf(type.uppercase())
                sahhaScoreTypes.add(scoreType)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha score type parameter is not valid")
            return
        }

        val startDateIsNotNull = startDateEpochMilli != null
        val endDateIsNotNull = endDateEpochMilli != null

        if (startDateIsNotNull && endDateIsNotNull) {
            val defaultZoneId = ZoneId.systemDefault()
            val startInstant = Instant.ofEpochMilli(startDateEpochMilli!!)
            val startLocalDateTime = LocalDateTime.ofInstant(startInstant, defaultZoneId)
            val endInstant = Instant.ofEpochMilli(endDateEpochMilli!!)
            val endLocalDateTime = LocalDateTime.ofInstant(endInstant, defaultZoneId)

            Sahha.getScores(
                sahhaScoreTypes,
                Pair(startLocalDateTime, endLocalDateTime)
            ) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val gson = GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime::class.java,
                            JsonSerializer<ZonedDateTime> { src, _, _ ->
                                JsonPrimitive(src.toString())
                            }).create()
                    val valueJson = gson.toJson(value)
                    
                    val data = JSObject()
                    data.put("value", valueJson)
                    call.resolve(data)
                }
            }
        } else {
            Sahha.getScores(sahhaScoreTypes) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val gson = GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime::class.java,
                            JsonSerializer<ZonedDateTime> { src, _, _ ->
                                JsonPrimitive(src.toString())
                            }).create()
                    val valueJson = gson.toJson(value)
                    
                    val data = JSObject()
                    data.put("value", valueJson)
                    call.resolve(data)
                }
            }
        }
    }

    @PluginMethod
    fun getBiomarkers(call: PluginCall) {
        val categories: JSArray? = call.getArray("categories")
        val types: JSArray? = call.getArray("types")
        val startDateEpochMilli: Long? = call.getLong("startDateTime")
        println("startDateTime provided: $startDateEpochMilli")
        val endDateEpochMilli: Long? = call.getLong("endDateTime")
        println("endDateTime provided: $endDateEpochMilli")

        if (categories == null) {
            call.reject("Sahha getBiomarkers categories parameter is missing")
            return
        }

        if (types == null) {
            call.reject("Sahha getBiomarkers types parameter is missing")
            return
        }

        if (startDateEpochMilli == null) {
            call.reject("Sahha getBiomarkers startDateTime parameter is missing")
            return
        }

        if (endDateEpochMilli == null) {
            call.reject("Sahha getBiomarkers endDateTime parameter is missing")
            return
        }

        val sahhaBiomarkerCategories: MutableSet<SahhaBiomarkerCategory> =
            mutableSetOf<SahhaBiomarkerCategory>()
        try {
            for (i in 0 until categories.length()) {
                val category: String = categories.getString(i)
                val biomarkerCategory = SahhaBiomarkerCategory.valueOf(category.uppercase())
                sahhaBiomarkerCategories.add(biomarkerCategory)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha biomarker category parameter is not valid")
            return
        }

        val sahhaBiomarkerTypes: MutableSet<SahhaBiomarkerType> = mutableSetOf<SahhaBiomarkerType>()
        try {
            for (i in 0 until types.length()) {
                val type: String = types.getString(i)
                val biomarkerType = SahhaBiomarkerType.valueOf(type.uppercase())
                sahhaBiomarkerTypes.add(biomarkerType)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha biomarker type parameter is not valid")
            return
        }

        val startDateIsNotNull = startDateEpochMilli != null
        val endDateIsNotNull = endDateEpochMilli != null

        if (startDateIsNotNull && endDateIsNotNull) {
            val defaultZoneId = ZoneId.systemDefault()
            val startInstant = Instant.ofEpochMilli(startDateEpochMilli!!)
            val startLocalDateTime = LocalDateTime.ofInstant(startInstant, defaultZoneId)
            val endInstant = Instant.ofEpochMilli(endDateEpochMilli!!)
            val endLocalDateTime = LocalDateTime.ofInstant(endInstant, defaultZoneId)

            Sahha.getBiomarkers(
                sahhaBiomarkerCategories,
                sahhaBiomarkerTypes,
                Pair(startLocalDateTime, endLocalDateTime)
            ) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val gson = GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime::class.java,
                            JsonSerializer<ZonedDateTime> { src, _, _ ->
                                JsonPrimitive(src.toString())
                            }).create()
                    val valueJson = gson.toJson(value)
                    
                    val data = JSObject()
                    data.put("value", valueJson)
                    call.resolve(data)
                }
            }
        } else {
            Sahha.getBiomarkers(sahhaBiomarkerCategories, sahhaBiomarkerTypes) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val gson = GsonBuilder()
                        .registerTypeAdapter(ZonedDateTime::class.java,
                            JsonSerializer<ZonedDateTime> { src, _, _ ->
                                JsonPrimitive(src.toString())
                            }).create()
                    val valueJson = gson.toJson(value)
                    
                    val data = JSObject()
                    data.put("value", valueJson)
                    call.resolve(data)
                }
            }
        }
    }

    @PluginMethod
    fun getStats(call: PluginCall) {
        val sensor: String? = call.getString("sensor")
        println("sensor provided: $sensor")
        val startDateEpochMilli: Long? = call.getLong("startDateTime")
        println("startDateTime provided: $startDateEpochMilli")
        val endDateEpochMilli: Long? = call.getLong("endDateTime")
        println("endDateTime provided: $endDateEpochMilli")

        if (sensor == null) {
            call.reject("Sahha getStats sensor parameter is missing")
            return
        }

        if (startDateEpochMilli == null) {
            call.reject("Sahha getStats startDateTime parameter is missing")
            return
        }

        if (endDateEpochMilli == null) {
            call.reject("Sahha getStats endDateTime parameter is missing")
            return
        }

        val defaultZoneId = ZoneId.systemDefault()
        val startInstant = Instant.ofEpochMilli(startDateEpochMilli)
        val startLocalDateTime = LocalDateTime.ofInstant(startInstant, defaultZoneId)
        val endInstant = Instant.ofEpochMilli(endDateEpochMilli)
        val endLocalDateTime = LocalDateTime.ofInstant(endInstant, defaultZoneId)

        Sahha.getStats(
            sensor = SahhaSensor.valueOf(sensor.uppercase()),
            Pair(startLocalDateTime, endLocalDateTime)
        ) { error, value ->
            if (error != null) {
                if (error.contains("found", ignoreCase = true)) {
                    val data = JSObject()
                    data.put("value", "[]")
                    call.resolve(data)
                } else {
                    call.reject(error)
                }
            } else {
                val gson = GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime::class.java,
                        JsonSerializer<ZonedDateTime> { src, _, _ ->
                            JsonPrimitive(src.toString())
                        }).create()

                val statsJson = gson.toJson(value)
                val data = JSObject()
                data.put("value", statsJson)
                call.resolve(data)
            }
        }
    }

    @PluginMethod
    fun getSamples(call: PluginCall) {
        val sensor: String? = call.getString("sensor")
        println("sensor provided: $sensor")
        val startDateEpochMilli: Long? = call.getLong("startDateTime")
        println("startDateTime provided: $startDateEpochMilli")
        val endDateEpochMilli: Long? = call.getLong("endDateTime")
        println("endDateTime provided: $endDateEpochMilli")

        if (sensor == null) {
            call.reject("Sahha getSamples sensor parameter is missing")
            return
        }

        if (startDateEpochMilli == null) {
            call.reject("Sahha getSamples startDateTime parameter is missing")
            return
        }

        if (endDateEpochMilli == null) {
            call.reject("Sahha getSamples endDateTime parameter is missing")
            return
        }

        val defaultZoneId = ZoneId.systemDefault()
        val startInstant = Instant.ofEpochMilli(startDateEpochMilli)
        val startLocalDateTime = LocalDateTime.ofInstant(startInstant, defaultZoneId)
        val endInstant = Instant.ofEpochMilli(endDateEpochMilli)
        val endLocalDateTime = LocalDateTime.ofInstant(endInstant, defaultZoneId)

        Sahha.getSamples(
            sensor = SahhaSensor.valueOf(sensor.uppercase()),
            Pair(startLocalDateTime, endLocalDateTime)
        ) { error, value ->
            if (error != null) {
                if (error.contains("found", ignoreCase = true)) {
                    val data = JSObject()
                    data.put("value", "[]")
                    call.resolve(data)
                } else {
                    call.reject(error)
                }
            } else {
                val gson = GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime::class.java,
                        JsonSerializer<ZonedDateTime> { src, _, _ ->
                            JsonPrimitive(src.toString())
                        }).create()

                val samplesJson = gson.toJson(value)
                val data = JSObject()
                data.put("value", samplesJson)
                call.resolve(data)
            }
        }
    }

    @Deprecated(message = "postSensorData is only supported on iOS", level = DeprecationLevel.WARNING)
    @PluginMethod
    fun postSensorData(call: PluginCall) {
        Log.w(TAG, "postSensorData is only supported on iOS")
        call.resolve()
    }

    @PluginMethod
    fun openAppSettings(call: PluginCall) {
        Sahha.openAppSettings()
        call.resolve()
    }

    fun stringToDrawableResource(context: Context, iconString: String?): Int? {
        return try {
            context.resources.getIdentifier(iconString, "drawable", context.packageName)
        } catch (e: Exception) {
            null
        }
    }
}