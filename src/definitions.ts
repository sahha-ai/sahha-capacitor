export enum SahhaEnvironment {
  development = 'development',
  production = 'production',
}

export enum SahhaSensor {
  sleep = 'sleep',
  pedometer = 'pedometer',
  device = 'device',
}

export enum SahhaSensorStatus {
  pending = 0, /// Sensor data is pending User permission
  unavailable = 1, /// Sensor data is not supported by the User's device
  disabled = 2, /// Sensor data has been disabled by the User
  enabled = 3, /// Sensor data has been enabled by the User
}

export interface SahhaSettings {
  environment: SahhaEnvironment,
  sensors?: SahhaSensor[],
  postSensorDataManually?: boolean
}

export interface SahhaDemographic {
  age?: number,
  gender?: string,
  country?: string,
  birthCountry?: string
}

export interface SahhaPlugin {
  configure(options: { settings: SahhaSettings }): Promise<{ success: boolean }>;
  authenticate(options: { profileToken: string, refreshToken: string }): Promise<{ success: boolean }>;
  getDemographic(): Promise<{ value: string }>;
  postDemographic(options: { demographic: SahhaDemographic }): Promise<{ success: boolean }>;
  getSensorStatus(options: { sensor: SahhaSensor }): Promise<{ status: SahhaSensorStatus }>;
  enableSensor(options: { sensor: SahhaSensor }): Promise<{ status: SahhaSensorStatus }>;
  analyze(options?: { startDate: Date, endDate: Date }): Promise<{ value: string }>;
  openAppSettings(): Promise<void>;
}