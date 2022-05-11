import Foundation

@objc public class Sahha: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
