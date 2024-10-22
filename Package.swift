// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SahhaCapacitor",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "SahhaCapacitor",
            targets: ["SahhaPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main"),
        .package(url: "https://github.com/sahha-ai/sahha-swift.git", branch: "main")
    ],
    targets: [
        .target(
            name: "SahhaPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Sahha", package: "sahha-swift")
            ],
            path: "ios/Sources/SahhaPlugin"),
        .testTarget(
            name: "SahhaPluginTests",
            dependencies: ["SahhaPlugin"],
            path: "ios/Tests/SahhaPluginTests")
    ]
)