package com.sahha.capacitor

import android.util.Log
import androidx.annotation.NonNull
import com.getcapacitor.*
import com.getcapacitor.annotation.CapacitorPlugin
import sdk.sahha.android.source.*
import java.util.*
import com.google.gson.Gson
import org.json.JSONArray

@CapacitorPlugin(name = "Sahha")
class SahhaPlugin : Plugin() {

    @PluginMethod
    fun echo(call: PluginCall) {
        val value = call.getString("value")
        val ret = JSObject()
        ret.put("value", value)
        call.resolve(ret)
    }

    @PluginMethod
    fun configure(call: PluginCall) {

        var settings: JSObject? = call.getObject("settings")

        if (settings == null) {
            call.reject("Sahha settings parameter is missing")
            return
        }

        var environment: String? = settings.getString("environment")
        if (environment == null) {
            call.reject("Sahha settings environment parameter is missing")
            return
        }

        var sahhaEnvironment: SahhaEnvironment
        try {
            sahhaEnvironment = SahhaEnvironment.valueOf(environment)
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha configure environment parameter is not valid")
            return
        }

        var sahhaSensors: MutableSet<SahhaSensor> = mutableSetOf<SahhaSensor>()
        if (settings.has("sensors")) {
            try {
                var sensors: JSONArray = settings.getJSONArray("sensors")
                for (i in 0 until sensors.length()) {
                    var sensor: String = sensors.getString(i)
                    var sahhaSensor = SahhaSensor.valueOf(sensor)
                    sahhaSensors.add(sahhaSensor)
                }
            } catch (e: IllegalArgumentException) {
                call.reject("Sahha sensor parameter is not valid")
                return
            }
        } else {
            for (sensor in SahhaSensor.values()) {
                sahhaSensors.add(sensor)
            }
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

        var sahhaSettings = SahhaSettings(sahhaEnvironment, sahhaNotificationConfiguration, SahhaFramework.capacitor, sahhaSensors)

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
    fun getDemographic(call: PluginCall) {

        Sahha.getDemographic() { error, demographic ->
            if (error != null) {
                call.reject(error)
            } else if (demographic != null) {
                val gson = Gson()
                val demographicJson: String = gson.toJson(demographic)
                Log.d("Sahha", demographicJson)
                val data = JSObject()
                data.put("value", demographicJson)
                call.resolve(data)
            } else {
                call.reject("Sahha demographic not available")
            }
        }
    }

    @PluginMethod
    fun postDemographic(call: PluginCall) {

        var demographic:JSObject? = call.getObject("demographic")

        if (demographic == null) {
            call.reject("Sahha demographic parameter is missing")
            return
        }

        val age: Int? = demographic.getInt("age")
        val gender: String? = demographic.getString("gender")
        val country: String? = demographic.getString("country")
        val birthCountry: String? = demographic.getString("birthCountry")
        val ethnicity: String? = demographic.getString("ethnicity")
        val occupation: String? = demographic.getString("occupation")
        val industry: String? = demographic.getString("industry")
        val incomeRange: String? = demographic.getString("incomeRange")
        val education: String? = demographic.getString("education")
        val relationship: String? = demographic.getString("relationship")
        val locale: String? = demographic.getString("locale")
        val livingArrangement: String? = demographic.getString("livingArrangement")
        val birthDate: String? = demographic.getString("birthDate")

        var sahhaDemographic = SahhaDemographic(age, gender, country, birthCountry, ethnicity, occupation, industry, incomeRange, education, relationship, locale, livingArrangement, birthDate)
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

        Sahha.getSensorStatus(
            context,
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

        Sahha.enableSensors(context) { error, sensorStatus ->
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
    fun postSensorData(call: PluginCall) {

        Sahha.postSensorData { error, success ->
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
    fun analyze(call: PluginCall) {

        val startDate: Long? = call.getLong("startDate")
        if (startDate != null) {
            Log.d("Sahha", "startDate $startDate")
        } else {
            Log.d("Sahha", "startDate missing")
        }

        val endDate: Long? = call.getLong("endDate")
        if (endDate != null) {
            Log.d("Sahha", "endDate $endDate")
        } else {
            Log.d("Sahha", "endDate missing")
        }

        var includeSourceData: Boolean = false
        if (call.hasOption("includeSourceData")) {
            includeSourceData = call.getBoolean("includeSourceData") ?: false
        }
        Log.d("Sahha", "includeSourceData " + includeSourceData.toString())

        if (startDate != null && endDate != null) {
            Sahha.analyze(includeSourceData, Pair(Date(startDate), Date(endDate))) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else if (value != null) {
                    val data = JSObject()
                    data.put("value", value)
                    call.resolve(data)
                } else {
                    call.reject("Sahha analyze is not available")
                }
            }
        } else {
            Sahha.analyze(includeSourceData) { error, value ->
                if (error != null) {
                    call.reject(error)
                } else if (value != null) {
                    val data = JSObject()
                    data.put("value", value)
                    call.resolve(data)
                } else {
                    call.reject("Sahha analyze is not available")
                }
            }
        }
    }

    @PluginMethod
    fun openAppSettings(call: PluginCall) {
        Sahha.openAppSettings(context)
    }
}