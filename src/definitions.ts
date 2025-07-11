export enum SahhaEnvironment {
  sandbox = 'sandbox',
  production = 'production',
}

export enum SahhaSensor {
  gender = 'gender',
  date_of_birth = 'date_of_birth',
  sleep = 'sleep',
  steps = 'steps',
  floors_climbed = 'floors_climbed',
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
  running_speed = 'running_speed',
  running_power = 'running_power',
  running_ground_contact_time = 'running_ground_contact_time',
  running_stride_length = 'running_stride_length',
  running_vertical_oscillation = 'running_vertical_oscillation',
  six_minute_walk_test_distance = 'six_minute_walk_test_distance',
  stair_ascent_speed = 'stair_ascent_speed',
  stair_descent_speed = 'stair_descent_speed',
  walking_speed = 'walking_speed',
  walking_steadiness = 'walking_steadiness',
  walking_asymmetry_percentage = 'walking_asymmetry_percentage',
  walking_double_support_percentage = 'walking_double_support_percentage',
  walking_step_length = 'walking_step_length',
  energy_consumed = 'energy_consumed'
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

export enum SahhaBiomarkerCategory {
  activity = 'activity',
  body = 'body',
  characteristic = 'characteristic',
  reproductive = 'reproductive',
  sleep = 'sleep',
  vitals = 'vitals',
}

export enum SahhaBiomarkerType {
  steps = 'steps',
  floors_climbed = 'floors_climbed',
  active_hours = 'active_hours',
  active_duration = 'active_duration',
  activity_low_intensity_duration = 'activity_low_intensity_duration',
  activity_mid_intensity_duration = 'activity_mid_intensity_duration',
  activity_high_intensity_duration = 'activity_high_intensity_duration',
  activity_sedentary_duration = 'activity_sedentary_duration',
  active_energy_burned = 'active_energy_burned',
  total_energy_burned = 'total_energy_burned',
  height = 'height',
  weight = 'weight',
  body_mass_index = 'body_mass_index',
  body_fat = 'body_fat',
  fat_mass = 'fat_mass',
  lean_mass = 'lean_mass',
  waist_circumference = 'waist_circumference',
  resting_energy_burned = 'resting_energy_burned',
  age = 'age',
  biological_sex = 'biological_sex',
  date_of_birth = 'date_of_birth',
  menstrual_cycle_length = 'menstrual_cycle_length',
  menstrual_cycle_start_date = 'menstrual_cycle_start_date',
  menstrual_cycle_end_date = 'menstrual_cycle_end_date',
  menstrual_phase = 'menstrual_phase',
  menstrual_phase_start_date = 'menstrual_phase_start_date',
  menstrual_phase_end_date = 'menstrual_phase_end_date',
  menstrual_phase_length = 'menstrual_phase_length',
  sleep_start_time = 'sleep_start_time',
  sleep_end_time = 'sleep_end_time',
  sleep_duration = 'sleep_duration',
  sleep_debt = 'sleep_debt',
  sleep_interruptions = 'sleep_interruptions',
  sleep_in_bed_duration = 'sleep_in_bed_duration',
  sleep_awake_duration = 'sleep_awake_duration',
  sleep_light_duration = 'sleep_light_duration',
  sleep_rem_duration = 'sleep_rem_duration',
  sleep_deep_duration = 'sleep_deep_duration',
  sleep_regularity = 'sleep_regularity',
  sleep_latency = 'sleep_latency',
  sleep_efficiency = 'sleep_efficiency',
  heart_rate_resting = 'heart_rate_resting',
  heart_rate_sleep = 'heart_rate_sleep',
  heart_rate_variability_sdnn = 'heart_rate_variability_sdnn',
  heart_rate_variability_rmssd = 'heart_rate_variability_rmssd',
  respiratory_rate = 'respiratory_rate',
  respiratory_rate_sleep = 'respiratory_rate_sleep',
  oxygen_saturation = 'oxygen_saturation',
  oxygen_saturation_sleep = 'oxygen_saturation_sleep',
  vo2_max = 'vo2_max',
  blood_glucose = 'blood_glucose',
  blood_pressure_systolic = 'blood_pressure_systolic',
  blood_pressure_diastolic = 'blood_pressure_diastolic',
  body_temperature_basal = 'body_temperature_basal',
  skin_temperature_sleep = 'skin_temperature_sleep',
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
  getScores(options: {
    types: SahhaScoreType[];
    startDateTime: number;
    endDateTime: number;
  }): Promise<{ value: string }>;
  getBiomarkers(options: {
    categories: SahhaBiomarkerCategory[];
    types: SahhaBiomarkerType[];
    startDateTime: number;
    endDateTime: number;
  }): Promise<{ value: string }>;
  getStats(options: { sensor: SahhaSensor; startDateTime: number; endDateTime: number }): Promise<{ value: string }>;
  getSamples(options: { sensor: SahhaSensor; startDateTime: number; endDateTime: number }): Promise<{ value: string }>;
  openAppSettings(): Promise<void>;
  /**
   * @remarks
   * Only available on **iOS**. On Android, this method is a no-op.
   */
  postSensorData(): Promise<void>;
}
