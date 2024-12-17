import { WebPlugin } from '@capacitor/core';

import type {
  SahhaPlugin,
  SahhaSettings,
  SahhaDemographic,
  SahhaSensor,
  SahhaSensorStatus,
  SahhaScoreType,
  SahhaBiomarkerCategory,
  SahhaBiomarkerType,
} from './definitions';

export class SahhaWeb extends WebPlugin implements SahhaPlugin {
  async configure(options: { settings: SahhaSettings }): Promise<{ success: boolean }> {
    console.log('configure', options);
    return { success: true };
  }

  async isAuthenticated(): Promise<{ success: boolean }> {
    console.log('isAuthenticated');
    return { success: true };
  }

  async authenticate(options: { appId: string; appSecret: string; externalId: string }): Promise<{ success: boolean }> {
    console.log('authenticate', options);
    return { success: true };
  }

  async authenticateToken(options: { profileToken: string; refreshToken: string }): Promise<{ success: boolean }> {
    console.log('authenticateToken', options);
    return { success: true };
  }

  async deauthenticate(): Promise<{ success: boolean }> {
    console.log('deauthenticate');
    return { success: true };
  }

  async getProfileToken(): Promise<{ profileToken?: string }> {
    console.log('getProfileToken');
    return { profileToken: 'ABC123' };
  }

  async getDemographic(): Promise<{ demographic?: string }> {
    console.log('getDemographic');
    return { demographic: `{ "gender" : "Male", "age" : 40 }` };
  }

  async postDemographic(options: { demographic: SahhaDemographic }): Promise<{ success: boolean }> {
    console.log('postDemographic', options);
    return { success: true };
  }

  async getSensorStatus(options: { sensors: SahhaSensor[] }): Promise<{ status: SahhaSensorStatus }> {
    console.log('getSensorStatus', options);
    return { status: 0 };
  }

  async enableSensors(options: { sensors: SahhaSensor[] }): Promise<{ status: SahhaSensorStatus }> {
    console.log('enableSensors', options);
    return { status: 2 };
  }

  async getScores(options: {
    types: SahhaScoreType[];
    startDate?: number;
    endDate?: number;
  }): Promise<{ value: string }> {
    console.log('getScores', options);
    console.log('startDate', options.startDate);
    console.log('endDate', options.endDate);
    return {
      value:
        '[{"category":"activity","unit":"count","valueType":"long","aggregation":"total","startDateTime":"2024-11-23T15:00:00+00:00","periodicity":"daily","value":"40","type":"steps","endDateTime":"2024-11-24T14:59:59+00:00"},{"category":"activity","unit":"count","valueType":"long","aggregation":"total","startDateTime":"2024-11-24T15:00:00+00:00","periodicity":"daily","value":"3662","type":"steps","endDateTime":"2024-11-25T14:59:59+00:00"},{"category":"activity","unit":"count","valueType":"long","aggregation":"total","startDateTime":"2024-11-25T15:00:00+00:00","periodicity":"daily","value":"8878","type":"steps","endDateTime":"2024-11-26T14:59:59+00:00"},{"category":"activity","unit":"count","valueType":"long","aggregation":"total","startDateTime":"2024-11-26T15:00:00+00:00","periodicity":"daily","value":"4442","type":"steps","endDateTime":"2024-11-27T14:59:59+00:00"}]',
    };
  }

  async getBiomarkers(options: {
    categories: SahhaBiomarkerCategory[];
    types: SahhaBiomarkerType[];
    startDate?: number;
    endDate?: number;
  }): Promise<{ value: string }> {
    console.log('getBiomarkers categories', options.categories);
    console.log('getBiomarkers types', options.types);
    console.log('startDate', options.startDate);
    console.log('endDate', options.endDate);

    return {
      value: 'PLACEHOLDER',
    };
  }

  async openAppSettings(): Promise<void> {
    console.log('openAppSettings');
    return;
  }
}
