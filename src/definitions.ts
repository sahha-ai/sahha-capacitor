export enum SahhaEnvironment {
  sandbox = 'sandbox',
  production = 'production',
}

export enum SahhaSensor {
  gender = 'gender',
  date_of_birth = 'date_of_birth',
  sleep = 'sleep',
  steps = 'steps',
  floor_count = 'floor_count',
  heart_rate = 'heart_rate',
  resting_heart_rate = 'resting_heart_rate',
  walking_heart_rate_average = 'walking_heart_rate_average',
  heart_rate_variability_sdnn = 'heart_rate_variability_sdnn',
  heart_rate_variability_rmssd = 'heart_rate_variability_rmssd',
  blood_pressure_systolic = 'blood_pressure_systolic',
  blood_pressure_diastolic = 'blood_pressure_diastolic',
  blood_glucose = 'blood_glucose',
  vo2_max = 'vo2_max',
  oxygen_saturation = 'oxygen_saturation',
  respiratory_rate = 'respiratory_rate',
  active_energy_burned = 'active_energy_burned',
  basal_energy_burned = 'basal_energy_burned',
  total_energy_burned = 'total_energy_burned',
  basal_metabolic_rate = 'basal_metabolic_rate',
  time_in_daylight = 'time_in_daylight',
  body_temperature = 'body_temperature',
  basal_body_temperature = 'basal_body_temperature',
  sleeping_wrist_temperature = 'sleeping_wrist_temperature',
  height = 'height',
  weight = 'weight',
  lean_body_mass = 'lean_body_mass',
  body_mass_index = 'body_mass_index',
  body_fat = 'body_fat',
  body_water_mass = 'body_water_mass',
  bone_mass = 'bone_mass',
  waist_circumference = 'waist_circumference',
  stand_time = 'stand_time',
  move_time = 'move_time',
  exercise_time = 'exercise_time',
  activity_summary = 'activity_summary',
  device_lock = 'device_lock',
  exercise = 'exercise',
}

export enum SahhaSensorStatus {
  pending = 0, /// Sensor data is pending User permission
  unavailable = 1, /// Sensor data is not supported by the User's device
  disabled = 2, /// Sensor data has been disabled by the User
  enabled = 3, /// Sensor data has been enabled by the User
}

export enum SahhaScoreType {
  wellbeing = 'wellbeing',
  activity = 'activity',
  sleep = 'sleep',
  readiness = 'readiness',
  mental_wellbeing = 'mental_wellbeing',
}

export interface SahhaSettings {
  environment: SahhaEnvironment;
  sensors?: SahhaSensor[];
  notificationSettings?: {
    icon?: string;
    title?: string;
    shortDescription?: string;
  };
}

export interface SahhaDemographic {
  age?: number;
  gender?: string;
  country?: string;
  birthCountry?: string;
  ethnicity?: string;
  occupation?: string;
  industry?: string;
  incomeRange?: string;
  education?: string;
  relationship?: string;
  locale?: string;
  livingArrangement?: string;
  birthDate?: string;
}

export interface SahhaPlugin {
  configure(options: { settings: SahhaSettings }): Promise<{ success: boolean }>;
  isAuthenticated(): Promise<{ success: boolean }>;
  authenticate(options: { appId: string; appSecret: string; externalId: string }): Promise<{ success: boolean }>;
  authenticateToken(options: { profileToken: string; refreshToken: string }): Promise<{ success: boolean }>;
  deauthenticate(): Promise<{ success: boolean }>;
  getProfileToken(): Promise<{ profileToken?: string }>;
  getDemographic(): Promise<{ demographic?: string }>;
  postDemographic(options: { demographic: SahhaDemographic }): Promise<{ success: boolean }>;
  getSensorStatus(options: { sensors: SahhaSensor[] }): Promise<{ status: SahhaSensorStatus }>;
  enableSensors(options: { sensors: SahhaSensor[] }): Promise<{ status: SahhaSensorStatus }>;
  openAppSettings(): Promise<void>;
}
