#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(SahhaPlugin, "Sahha",
           CAP_PLUGIN_METHOD(configure, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(authenticate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDemographic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(postDemographic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getSensorStatus, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(enableSensors, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(postSensorData, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(analyze, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openAppSettings, CAPPluginReturnNone);
)
