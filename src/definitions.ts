export enum SahhaEnvironment {
  development = 'development',
  production = 'production',
}

export enum SahhaSensor {
  sleep = 'sleep',
  pedometer = 'pedometer',
  device = 'device',
  heart = 'heart',
  blood = 'blood',
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
  notificationSettings?: {
    icon?: string,
    title?: string,
    shortDescription?: string,
  },
}

export interface SahhaDemographic {
  age?: number,
  gender?: string,
  country?: string,
  birthCountry?: string,
  ethnicity?: string,
  occupation?: string,
  industry?: string,
  incomeRange?: string,
  education?: string,
  relationship?: string,
  locale?: string,
  livingArrangement?: string
}

export interface SahhaPlugin {
  configure(options: { settings: SahhaSettings }): Promise<{ success: boolean }>;
  authenticate(options: { appId: string, appSecret: string, externalId: string }): Promise<{ success: boolean }>;
  getDemographic(): Promise<{ value: string }>;
  postDemographic(options: { demographic: SahhaDemographic }): Promise<{ success: boolean }>;
  getSensorStatus(): Promise<{ status: SahhaSensorStatus }>;
  enableSensors(): Promise<{ status: SahhaSensorStatus }>;
  postSensorData(): Promise<{ success: boolean }>;
  analyze(options?: {
    startDate?: number, endDate?: number, includeSourceData?: boolean
  }): Promise<{ value: string }>;
  openAppSettings(): Promise<void>;
}