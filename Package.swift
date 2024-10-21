// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SahhaCapacitor",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "SahhaCapacitor",
            targets: ["SahhaPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "SahhaPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/SahhaPlugin"),
        .testTarget(
            name: "SahhaPluginTests",
            dependencies: ["SahhaPlugin"],
            path: "ios/Tests/SahhaPluginTests")
    ]
)