package com.sahha.capacitor;

import android.util.Log
import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.google.gson.GsonBuilder
import sdk.sahha.android.source.Sahha
import sdk.sahha.android.source.SahhaConverterUtility
import sdk.sahha.android.source.SahhaDemographic
import sdk.sahha.android.source.SahhaEnvironment
import sdk.sahha.android.source.SahhaFramework
import sdk.sahha.android.source.SahhaNotificationConfiguration
import sdk.sahha.android.source.SahhaScoreType
import sdk.sahha.android.source.SahhaSensor
import sdk.sahha.android.source.SahhaSettings


@CapacitorPlugin(name = "Sahha")
public class SahhaPlugin : Plugin() {

    @PluginMethod
    fun configure(call: PluginCall) {

        var settings: JSObject? = call.getObject("settings")

        if (settings == null) {
            call.reject("Sahha configure settings parameter is missing")
            return
        }

        var environment: String? = settings.getString("environment")
        if (environment == null) {
            call.reject("Sahha configure settings environment parameter is missing")
            return
        }

        var sahhaEnvironment: SahhaEnvironment
        try {
            sahhaEnvironment = SahhaEnvironment.valueOf(environment)
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha configure settings environment parameter is not valid")
            return
        }

        // Notification config
        var sahhaNotificationConfiguration: SahhaNotificationConfiguration? = null
        try {
            settings.getJSObject("notificationSettings")?.also { nSettings ->
                val icon = nSettings.getString("icon")
                val title = nSettings.getString("title")
                val shortDescription = nSettings.getString("shortDescription")

                sahhaNotificationConfiguration = SahhaNotificationConfiguration(
                    SahhaConverterUtility.stringToDrawableResource(
                        context,
                        icon
                    ),
                    title,
                    shortDescription,
                )
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha.configure() notification config is not valid")
            return
        }
        // Notification config ends

        var sahhaSettings = SahhaSettings(
            sahhaEnvironment,
            sahhaNotificationConfiguration,
            SahhaFramework.capacitor
        )

        var app = activity?.application
        if (app == null) {
            call.reject("Sahha configure app is missing")
        } else {
            Sahha.configure(app, sahhaSettings) { error, success ->
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
        val country: String? = demographic.optString("country")
        val birthCountry: String? = demographic.optString("birthCountry")
        val ethnicity: String? = demographic.optString("ethnicity")
        val occupation: String? = demographic.optString("occupation")
        val industry: String? = demographic.optString("industry")
        val incomeRange: String? = demographic.optString("incomeRange")
        val education: String? = demographic.optString("education")
        val relationship: String? = demographic.optString("relationship")
        val locale: String? = demographic.optString("locale")
        val livingArrangement: String? = demographic.optString("livingArrangement")
        val birthDate: String? = demographic.optString("birthDate")

        var sahhaDemographic = SahhaDemographic(
            age = if (age == 0) null else age,
            gender = gender?.ifEmpty { null },
            country = country?.ifEmpty { null },
            birthCountry = birthCountry?.ifEmpty { null },
            ethnicity = ethnicity?.ifEmpty { null },
            occupation = occupation?.ifEmpty { null },
            industry = industry?.ifEmpty { null },
            incomeRange = incomeRange?.ifEmpty { null },
            education = education?.ifEmpty { null },
            relationship = relationship?.ifEmpty { null },
            locale = locale?.ifEmpty { null },
            livingArrangement = livingArrangement?.ifEmpty { null },
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

    @PluginMethod
    fun getSensorStatus(call: PluginCall) {

        var sensors: JSArray? = call.getArray("sensors")

        if (sensors == null) {
            call.reject("Sahha getSensorStatus sensors parameter is missing")
            return
        }

        var sahhaSensors: MutableSet<SahhaSensor> = mutableSetOf<SahhaSensor>()
        try {
            for (i in 0 until sensors.length()) {
                var sensor: String = sensors.getString(i)
                var sahhaSensor = SahhaSensor.valueOf(sensor)
                sahhaSensors.add(sahhaSensor)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha sensor parameter is not valid")
            return
        }

        Sahha.getSensorStatus(
            context,
            sahhaSensors,
        ) { error, sensorStatus ->
            if (error != null) {
                call.reject(error)
            } else {
                val data = JSObject()
                data.put("status", sensorStatus.ordinal)
                call.resolve(data)
            }
        }
    }

    @PluginMethod
    fun enableSensors(call: PluginCall) {

        var sensors: JSArray? = call.getArray("sensors")

        if (sensors == null) {
            call.reject("Sahha enableSensors sensors parameter is missing")
            return
        }

        var sahhaSensors: MutableSet<SahhaSensor> = mutableSetOf<SahhaSensor>()
        try {
            for (i in 0 until sensors.length()) {
                var sensor: String = sensors.getString(i)
                var sahhaSensor = SahhaSensor.valueOf(sensor)
                sahhaSensors.add(sahhaSensor)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha sensor parameter is not valid")
            return
        }

        Sahha.enableSensors(context, sahhaSensors) { error, sensorStatus ->
            if (error != null) {
                call.reject(error)
            } else {
                val data = JSObject()
                data.put("status", sensorStatus.ordinal)
                call.resolve(data)
            }
        }
    }

    @PluginMethod
    fun getScores(call: PluginCall) {
        val types: JSArray? = call.getArray("types")

        if (types == null) {
            call.reject("Sahha getScores types parameter is missing")
            return
        }

        val sahhaScoreTypes: MutableSet<SahhaScoreType> = mutableSetOf<SahhaScoreType>()
        try {
            for (i in 0 until types.length()) {
                val type: String = types.getString(i)
                val scoreType = SahhaScoreType.valueOf(type)
                sahhaScoreTypes.add(scoreType)
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha score type parameter is not valid")
            return
        }

        Sahha.getScores(sahhaScoreTypes) { error, value ->
            if (error != null) {
                call.reject(error)
            } else {
                val data = JSObject()
                data.put("value", value)
                call.resolve(data)
            }
        }
    }

    @PluginMethod
    fun openAppSettings(call: PluginCall) {
        Sahha.openAppSettings(context)
    }
}