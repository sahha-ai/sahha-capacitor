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

        var postSensorDataManually: Boolean = settings.getBoolean("postSensorDataManually", false) ?: false

        var sahhaSettings = SahhaSettings(sahhaEnvironment, SahhaFramework.capacitor, sahhaSensors, postSensorDataManually)

        var app = activity?.application
        if (app == null) {
            call.reject("Sahha configure app is missing")
        } else {
            Sahha.configure(app, sahhaSettings)
            val data = JSObject()
            data.put("success", true)
            call.resolve(data)
        }
    }

    @PluginMethod
    fun authenticate(call: PluginCall) {

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

        var sahhaDemographic = SahhaDemographic(age, gender, country, birthCountry)
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

        var sensor: String? = call.getString("sensor")
        if (sensor == null) {
            call.reject("Sahha sensor parameter is missing")
            return
        }

        try {
            var sahhaSensor = SahhaSensor.valueOf(sensor)
            Sahha.getSensorStatus(
                context,
                sahhaSensor
            ) { error, sensorStatus ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val data = JSObject()
                    data.put("status", sensorStatus.ordinal)
                    call.resolve(data)
                }
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha sensor parameter is not valid")
        }
    }

    @PluginMethod
    fun enableSensor(call: PluginCall) {

        var sensor: String? = call.getString("sensor")
        if (sensor == null) {
            call.reject("Sahha sensor parameter is missing")
            return
        }

        try {
            var sahhaSensor = SahhaSensor.valueOf(sensor)
            Sahha.enableSensor(
                context,
                sahhaSensor
            ) { error, sensorStatus ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val data = JSObject()
                    data.put("status", sensorStatus.ordinal)
                    call.resolve(data)
                }
            }
        } catch (e: IllegalArgumentException) {
            call.reject("Sahha sensor parameter is not valid")
        }
    }

    @PluginMethod
    fun postSensorData(call: PluginCall) {

        var sensors: JSArray? = call.getArray("sensors")
        if (sensors == null) {
            Sahha.postSensorData { error, success ->
                if (error != null) {
                    call.reject(error)
                } else {
                    val data = JSObject()
                    data.put("success", success)
                    call.resolve(data)
                }
            }
        } else {
            var sahhaSensors: MutableSet<SahhaSensor> = mutableSetOf<SahhaSensor>()
            for (sensor in sensors.toList<String>()) {
                try {
                    var sahhaSensor = SahhaSensor.valueOf(sensor)
                    sahhaSensors.add(sahhaSensor)
                } catch (e: IllegalArgumentException) {
                    call.reject("Sahha sensor parameter $sensor is not valid")
                    return
                }
            }
            Sahha.postSensorData(sahhaSensors) { error, success ->
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
            includeSourceData = call.getBoolean("includeSourceData")
        }
        Log.d("Sahha", "includeSourceData " + includeSourceData.toString())

        if (startDate != null && endDate != null) {
            Sahha.analyze(Pair(Date(startDate), Date(endDate))) { error, value ->
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
            Sahha.analyze() { error, value ->
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