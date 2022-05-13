package com.sahha.capacitor

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import sdk.sahha.android.source.*

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

        if (settings != null) {

            var environment: String? = settings.getString("environment")
            if (environment != null) {

                var sahhaEnvironment: SahhaEnvironment
                try {
                    sahhaEnvironment = SahhaEnvironment.valueOf(environment)
                } catch (e: IllegalArgumentException) {
                    call.reject("Sahha configure environment parameter is not valid")
                    return
                }

                var sahhaSettings = SahhaSettings(sahhaEnvironment)

                var app = activity?.application
                if (app != null) {
                    Sahha.configure(app, SahhaSettings(SahhaEnvironment.development))
                    val data = JSObject()
                    data.put("success", true)
                    call.resolve(data)
                } else {
                    call.reject("Sahha configure app is missing")
                }
            } else {
            call.reject("Sahha settings environment parameter is missing")
        }
        } else {
            call.reject("Sahha settings parameter is missing")
        }
    }
/*
    @objc func authenticate(_ call: CAPPluginCall) {
        guard let profileToken = call.getString("profileToken") else {
            call.reject("Sahha authenticate profileToken parameter is missing")
            return
        }
        guard let refreshToken = call.getString("refreshToken") else {
            call.reject("Sahha authenticate refreshToken parameter is missing")
            return
        }

        let success = Sahha.authenticate(profileToken: profileToken, refreshToken: refreshToken)
        if success {
            call.resolve([
                "success": true
            ])
        } else {
            call.reject("Sahha authenticate was not successful")
        }
    }

    @objc func getDemographic(_ call: CAPPluginCall) {
        Sahha.getDemographic { error, value in
            if let error = error {
                call.reject(error)
            } else if let value = value {
                do {
                    let jsonEncoder = JSONEncoder()
                    jsonEncoder.outputFormatting = .prettyPrinted
                            let jsonData = try jsonEncoder.encode(value)
                        if let string = String(data: jsonData, encoding: .utf8) {
                            call.resolve(["value": string])
                        } else {
                            call.reject("Sahha demographic data error")
                        }
                    } catch let encodingError {
                        print(encodingError)
                        call.reject(encodingError.localizedDescription)
                    }
                } else {
                call.reject("Sahha demographic is not available")
            }
        }
    }

    @objc func postDemographic(_ call: CAPPluginCall) {
        if let demographic = call.getObject("demographic") {

            var sahhaDemographic = SahhaDemographic()

            if let ageNumber = demographic["age"] as? NSNumber {
                let age = ageNumber.intValue
                        sahhaDemographic.age = age
            }

            if let gender = demographic["gender"] as? String {
                sahhaDemographic.gender = gender
            }

            if let country = demographic["country"] as? String {
                sahhaDemographic.country = country
            }

            if let birthCountry = demographic["birthCountry"] as? String {
                sahhaDemographic.birthCountry = birthCountry
            }

            Sahha.postDemographic(sahhaDemographic) { error, success in
                if let error = error {
                    call.reject(error)
                } else {
                    call.resolve(["success" : success])
                }
            }

        } else {
            call.reject("Sahha demographic parameter is missing")
        }
    }
*/
@PluginMethod
fun getSensorStatus(call: PluginCall) {

    var sensor: String? = call.getString("sensor")
    if (sensor != null) {
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
    } else {
        call.reject("Sahha sensor parameter is missing")
    }
}

    @PluginMethod
    fun enableSensor(call: PluginCall) {

        var sensor: String? = call.getString("sensor")
        if (sensor != null) {
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
        } else {
            call.reject("Sahha sensor parameter is missing")
        }
    }

    /*
    @PluginMethod
    fun analyze(call: PluginCall) {
    val name: String = call.getObject("startDate")
        if let startDate = call.getDate("startDate"), let endDate = call.getDate("endDate") {
            Sahha.analyze(dates: (startDate: startDate, endDate: endDate)) { error, value in
            if let error = error {
                call.reject(error)
            } else if let value = value {
                call.resolve([
                    "value": value
                ]
                )
            } else {
                call.reject("Sahha analyze is not available")
            }
        }
        } else {
            Sahha.analyze { error, value in
                if let error = error {
                    call.reject(error)
                } else if let value = value {
                    call.resolve([
                        "value": value
                    ]
                    )
                } else {
                    call.reject("Sahha analyze is not available")
                }
            }
        }
    }
*/

    @PluginMethod
    fun openAppSettings(call: PluginCall) {
        Sahha.openAppSettings(context.applicationContext)
    }
}