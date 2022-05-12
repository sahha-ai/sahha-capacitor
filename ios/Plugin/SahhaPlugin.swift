import Foundation
import Capacitor
import Sahha

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SahhaPlugin)
public class SahhaPlugin: CAPPlugin {
    
    @objc func configure(_ call: CAPPluginCall) {
        if let settings = call.getObject("settings") {
            if let environment = settings["environment"] as? String, let sahhaEnvironment = SahhaEnvironment(rawValue: environment) {
                var sahhaSettings = SahhaSettings(environment: sahhaEnvironment)
                if let sensors = settings["sensors"] as? [String] {
                    var sahhaSensors: Set<SahhaSensor> = []
                    for sensor in sensors {
                        if let sahhaSensor = SahhaSensor(rawValue: sensor) {
                            sahhaSensors.insert(sahhaSensor)
                        }
                    }
                    sahhaSettings.sensors = sahhaSensors
                }
                if let postSensorDataManually = settings["postSensorDataManually"] as? Bool {
                    sahhaSettings.postSensorDataManually = postSensorDataManually
                }
                print(sahhaSettings)
                Sahha.configure(sahhaSettings)
                call.resolve([
                    "success": true
                ])
            } else {
                call.reject("Sahha settings environment parameter is missing")
            }
        } else {
            call.reject("Sahha settings parameter is missing")
        }
    }
    
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
    
    @objc func getSensorStatus(_ call: CAPPluginCall) {
        if let sensor = call.getString("sensor"), let sahhaSensor = SahhaSensor(rawValue: sensor) {
            Sahha.getSensorStatus(sahhaSensor) { sensorStatus in
                call.resolve([
                    "status": sensorStatus.rawValue
                    ]
                )
            }
        } else {
            call.reject("Sahha sensor parameter is not valid")
        }
    }
    
    @objc func enableSensor(_ call: CAPPluginCall) {
        if let sensor = call.getString("sensor"), let sahhaSensor = SahhaSensor(rawValue: sensor) {
            Sahha.enableSensor(sahhaSensor) { sensorStatus in
                call.resolve([
                    "status": sensorStatus.rawValue
                    ]
                )
            }
        } else {
            call.reject("Sahha sensor parameter is not valid")
        }
    }
    
    @objc func analyze(_ call: CAPPluginCall) {
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
    
    @objc func openAppSettings(_ call: CAPPluginCall) {
        Sahha.openAppSettings()
    }
}
