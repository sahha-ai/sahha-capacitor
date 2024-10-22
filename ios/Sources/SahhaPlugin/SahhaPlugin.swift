import Foundation
import Capacitor
import Sahha

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

@objc(SahhaPlugin)
public class SahhaPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SahhaPlugin"
    public let jsName = "Sahha"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAuthenticated", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "authenticate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "authenticateToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deauthenticate", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getProfileToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDemographic", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "postDemographic", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSensorStatus", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enableSensors", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openAppSettings", returnType: CAPPluginReturnPromise)
    ]
    
    @objc func configure(_ call: CAPPluginCall) {
        guard let settings = call.getObject("settings") else {
            call.reject("Sahha configure settings parameter is missing")
            return
        }
        
        guard let environment = settings["environment"] as? String, let sahhaEnvironment = SahhaEnvironment(rawValue: environment) else {
            call.reject("Sahha configure settings environment parameter is missing")
            return
        }
        var sahhaSettings = SahhaSettings(environment: sahhaEnvironment)
        sahhaSettings.framework = .capacitor
                
        Sahha.configure(sahhaSettings) {
            call.resolve([
                "success": true
            ])
        }
    }
    
    @objc func isAuthenticated(_ call: CAPPluginCall) {
        call.resolve([
            "success": Sahha.isAuthenticated
        ])
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
    
    @objc func authenticateToken(_ call: CAPPluginCall) {
        guard let profileToken = call.getString("profileToken") else {
            call.reject("Sahha authenticateToken profileToken parameter is missing")
            return
        }
        guard let refreshToken = call.getString("refreshToken") else {
            call.reject("Sahha authenticateToken refreshToken parameter is missing")
            return
        }
        
        Sahha.authenticate(profileToken: profileToken, refreshToken: refreshToken) { error, success in
            if let error = error {
                call.reject(error)
            } else {
                call.resolve([
                    "success": success
                ])
            }
        }
    }

    @objc func deauthenticate(_ call: CAPPluginCall) {
        Sahha.deauthenticate { error, success in
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
                        call.resolve(["demographic": string])
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
        guard let demographic = call.getObject("demographic") else {
            call.reject("Sahha postDemographic demographic parameter is missing")
            return
        }
        
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
    }
    
    @objc func getSensorStatus(_ call: CAPPluginCall) {
        guard let sensors = call.getArray("sensors") as? [String] else {
            call.reject("Sahha getSensorStatus sensors parameter is missing")
            return
        }
        
        var configSensors: Set<SahhaSensor> = []
        for sensor in sensors {
            if let configSensor = SahhaSensor(rawValue: sensor) {
                configSensors.insert(configSensor)
            }
        }
        
        Sahha.getSensorStatus(configSensors) { error, sensorStatus in
            if let error = error {
                call.reject(error)
            } else {
                call.resolve(["status" : sensorStatus.rawValue])
            }
        }
    }
    
    @objc func enableSensors(_ call: CAPPluginCall) {
        guard let sensors = call.getArray("sensors") as? [String] else {
            call.reject("Sahha enableSensors sensors parameter is missing")
            return
        }
        
        var configSensors: Set<SahhaSensor> = []
        for sensor in sensors {
            if let configSensor = SahhaSensor(rawValue: sensor) {
                configSensors.insert(configSensor)
            }
        }
        
        Sahha.enableSensors(configSensors) { error, sensorStatus in
            if let error = error {
                call.reject(error)
            } else {
                call.resolve(["status" : sensorStatus.rawValue])
            }
        }
    }
    
    @objc func openAppSettings(_ call: CAPPluginCall) {
        Sahha.openAppSettings()
    }
}
