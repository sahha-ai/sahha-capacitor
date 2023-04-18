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

                Sahha.configure(sahhaSettings) {
                    call.resolve([
                        "success": true
                    ])
                }

            } else {
                call.reject("Sahha settings environment parameter is missing")
            }
        } else {
            call.reject("Sahha settings parameter is missing")
        }
    }
    
    @objc func authenticate(_ call: CAPPluginCall) {
        guard let appId = call.getString("appId") else {
            call.reject("Sahha authenticate appId parameter is missing")
            return
        }
        guard let appSecret = call.getString("appSecret") else {
            call.reject("Sahha authenticate appSecret parameter is missing")
            return
        }
        guard let externalId = call.getString("externalId") else {
            call.reject("Sahha authenticate externalId parameter is missing")
            return
        }
        
        Sahha.authenticate(appId: appId, appSecret: appSecret, externalId: externalId) { error, success in
            
            if let error = error {
                call.reject(error)
            } else {
                call.resolve([
                    "success": success
                ])
            }
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
            
            if let ethnicity = demographic["ethnicity"] as? String {
                sahhaDemographic.ethnicity = ethnicity
            }

            if let occupation = demographic["occupation"] as? String {
                sahhaDemographic.occupation = occupation
            }

            if let industry = demographic["industry"] as? String {
                sahhaDemographic.industry = industry
            }

            if let incomeRange = demographic["incomeRange"] as? String {
                sahhaDemographic.incomeRange = incomeRange
            }

            if let education = demographic["education"] as? String {
                sahhaDemographic.education = education
            }

            if let relationship = demographic["relationship"] as? String {
                sahhaDemographic.relationship = relationship
            }

            if let locale = demographic["locale"] as? String {
                sahhaDemographic.locale = locale
            }

            if let livingArrangement = demographic["livingArrangement"] as? String {
                sahhaDemographic.livingArrangement = livingArrangement
            }
            
            if let birthDate = demographic["birthDate"] as? String {
                sahhaDemographic.birthDate = birthDate
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
        Sahha.getSensorStatus { sensorStatus in
            call.resolve([
                "status": sensorStatus.rawValue
            ]
            )
        }
    }
    
    @objc func enableSensors(_ call: CAPPluginCall) {
        Sahha.enableSensors { sensorStatus in
            call.resolve([
                "status": sensorStatus.rawValue
            ]
            )
        }
    }
    
    @objc func postSensorData(_ call: CAPPluginCall) {
        Sahha.postSensorData { error, success in
            if let error = error {
                call.reject(error)
            } else {
                call.resolve([
                    "success": success
                ]
                )
            }
        }
    }
    
    @objc func analyze(_ call: CAPPluginCall) {
        var dates: (startDate: Date, endDate: Date)?
        var includeSourceData: Bool = false
        if let startDateNumber = call.getDouble("startDate"), let endDateNumber = call.getDouble("endDate") {
            let startDate = Date(timeIntervalSince1970: TimeInterval(startDateNumber / 1000))
            let endDate = Date(timeIntervalSince1970: TimeInterval(endDateNumber / 1000))
            dates = (startDate, endDate)
            print("startDate", startDate.toTimezoneFormat)
            print("endDate", endDate.toTimezoneFormat)
        } else {
            print("no dates")
        }
        if let boolValue = call.getBool("includeSourceData") {
            includeSourceData = boolValue
            print("includeSourceData", includeSourceData)
        }
        
        Sahha.analyze(dates: dates, includeSourceData: includeSourceData) { error, value in
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
    
    @objc func openAppSettings(_ call: CAPPluginCall) {
        Sahha.openAppSettings()
    }
}
