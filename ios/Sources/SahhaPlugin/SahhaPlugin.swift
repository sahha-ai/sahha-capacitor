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
        CAPPluginMethod(name: "openAppSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getScores", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBiomarkers", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getStats", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSamples", returnType: CAPPluginReturnPromise),
    ]
    
    private func encodeJson<T: Encodable>(_ value: T) throws -> String {
        let jsonData = try JSONEncoder().encode(value)
        if let jsonString = String(data: jsonData, encoding: .utf8) {
            return jsonString
        } else {
            throw EncodingError.invalidValue(value, EncodingError.Context(codingPath: [], debugDescription: "Unable to encode value"))
        }
    }
    
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
                    let jsonString = try self.encodeJson(value)
                    call.resolve(["demographic": jsonString])
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
    
    @objc func getScores(_ call: CAPPluginCall) {
        guard let types = call.getArray("types", String.self) else {
            call.reject("Sahha getScores types parameter is missing")
            return
        }
        
        guard let startDateEpochMilli = call.getDouble("startDateTime") else {
            call.reject("Sahha getScores startDateTime parameter is missing")
            return
        }
        
        guard let endDateEpochMilli = call.getDouble("endDateTime") else {
            call.reject("Sahha getScores endDateTime parameter is missing")
            return
        }
        
        var sahhaScoreTypes = Set<SahhaScoreType>()
        for type in types {
            if let scoreType = SahhaScoreType(rawValue: type) {
                sahhaScoreTypes.insert(scoreType)
            } else {
                call.reject("Sahha score type parameter is invalid")
                return
            }
        }
        
        let startDate = Date(timeIntervalSince1970: startDateEpochMilli / 1000)
        let endDate = Date(timeIntervalSince1970: endDateEpochMilli / 1000)
        
        Sahha.getScores(types: sahhaScoreTypes, startDateTime: startDate, endDateTime: endDate) { error, scores in
            if let error = error {
                call.reject(error)
            } else if let scores = scores {
                call.resolve(["value": scores])
            } else {
                call.reject("No scores found")
            }
        }
    }
    
    @objc func getBiomarkers(_ call: CAPPluginCall) {
        guard let categories = call.getArray("categories", String.self) else {
            call.reject("Sahha getBiomarkers categories parameter is missing")
            return
        }
        
        guard let types = call.getArray("types", String.self) else {
            call.reject("Sahha getBiomarkers types parameter is missing")
            return
        }
        
        guard let startDateEpochMilli = call.getDouble("startDateTime") else {
            call.reject("Sahha getBiomarkers startDateTime parameter is missing")
            return
        }
        
        guard let endDateEpochMilli = call.getDouble("endDateTime") else {
            call.reject("Sahha getBiomarkers endDateTime parameter is missing")
            return
        }
        
        var sahhaBiomarkerCategories = Set<SahhaBiomarkerCategory>()
        for category in categories {
            if let biomarkerCategory = SahhaBiomarkerCategory(rawValue: category) {
                sahhaBiomarkerCategories.insert(biomarkerCategory)
            } else {
                call.reject("Sahha biomarker category parameter is invalid")
                return
            }
        }
        
        var sahhaBiomarkerTypes = Set<SahhaBiomarkerType>()
        for type in types {
            if let biomarkerType = SahhaBiomarkerType(rawValue: type) {
                sahhaBiomarkerTypes.insert(biomarkerType)
            } else {
                call.reject("Sahha biomarker type parameter is invalid")
                return
            }
        }
        
        let startDate = Date(timeIntervalSince1970: startDateEpochMilli / 1000)
        let endDate = Date(timeIntervalSince1970: endDateEpochMilli / 1000)
        
        Sahha.getBiomarkers(categories: sahhaBiomarkerCategories, types: sahhaBiomarkerTypes, startDateTime: startDate, endDateTime: endDate) { error, biomarkers in
            if let error = error {
                call.reject(error)
            } else if let biomarkers = biomarkers {
                call.resolve(["value": biomarkers])
            } else {
                call.reject("No biomarkers found")
            }
        }
    }
    
    @objc func getStats(_ call: CAPPluginCall) {
        guard let sensor = call.getString("sensor") else {
            call.reject("Sahha getStats sensor parameter is missing")
            return
        }
        
        guard let startDateEpochMilli = call.getDouble("startDateTime") else {
            call.reject("Sahha getStats startDateTime parameter is missing")
            return
        }
        
        guard let endDateEpochMilli = call.getDouble("endDateTime") else {
            call.reject("Sahha getStats endDateTime parameter is missing")
            return
        }
        
        let startDate = Date(timeIntervalSince1970: startDateEpochMilli / 1000)
        let endDate = Date(timeIntervalSince1970: endDateEpochMilli / 1000)
        
        if let sahhaSensor = SahhaSensor(rawValue: sensor) {
            Sahha.getStats(sensor: sahhaSensor, startDateTime: startDate, endDateTime: endDate) { error, stats in
                if let error = error {
                    call.reject(error)
                } else if !stats.isEmpty {
                    do {
                        let jsonEncoder = JSONEncoder()
                        jsonEncoder.outputFormatting = .prettyPrinted
                        let jsonData = try jsonEncoder.encode(stats)
                        if let string = String(data: jsonData, encoding: .utf8) {
                            call.resolve(["value": string])
                        } else {
                            call.reject("Encoding error")
                        }
                    } catch let encodingError {
                        print(encodingError)
                        Sahha.postError(
                            framework: .capacitor,
                            message: encodingError.localizedDescription,
                            path: "SahhaCapacitor", method: "getStats",
                            body: "jsonEncoder")
                        call.reject(encodingError.localizedDescription)
                        return
                    }
                    
                } else {
                    call.reject("No stats found")
                }
            }
        } else {
            call.reject("Invalid sensor: \(sensor)")
        }
    }
    
    @objc func getSamples(_ call: CAPPluginCall) {
        guard let sensor = call.getString("sensor") else {
            call.reject("Sahha getSamples sensor parameter is missing")
            return
        }
        
        guard let startDateEpochMilli = call.getDouble("startDateTime") else {
            call.reject("Sahha getSamples startDate parameter is missing")
            return
        }
        
        guard let endDateEpochMilli = call.getDouble("endDateTime") else {
            call.reject("Sahha getSamples endDate parameter is missing")
            return
        }
        
        let startDate = Date(timeIntervalSince1970: startDateEpochMilli / 1000)
        let endDate = Date(timeIntervalSince1970: endDateEpochMilli / 1000)
        
        if let sahhaSensor = SahhaSensor(rawValue: sensor) {
            Sahha.getSamples(sensor: sahhaSensor, startDateTime: startDate, endDateTime: endDate) { error, stats in
                if let error = error {
                    call.reject(error)
                } else if !stats.isEmpty {
                    do {
                        let jsonEncoder = JSONEncoder()
                        jsonEncoder.outputFormatting = .prettyPrinted
                        let jsonData = try jsonEncoder.encode(stats)
                        if let string = String(data: jsonData, encoding: .utf8) {
                            call.resolve(["value": string])
                        } else {
                            call.reject("Encoding error")
                        }
                    } catch let encodingError {
                        print(encodingError)
                        Sahha.postError(
                            framework: .capacitor,
                            message: encodingError.localizedDescription,
                            path: "SahhaCapacitor", method: "getSamples",
                            body: "jsonEncoder")
                        call.reject(encodingError.localizedDescription)
                        return
                    }
                    
                } else {
                    call.reject("No samples found")
                }
            }
        } else {
            call.reject("Invalid sensor: \(sensor)")
        }
    }
    
    @objc func postSensorData(_ call: CAPPluginCall) {
        Sahha.postSensorData()
    }
    
    @objc func openAppSettings(_ call: CAPPluginCall) {
        Sahha.openAppSettings()
    }
}
