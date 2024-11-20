import { WebPlugin } from '@capacitor/core';

import type {
  SahhaPlugin,
  SahhaSettings,
  SahhaDemographic,
  SahhaSensor,
  SahhaSensorStatus,
  SahhaScoreType,
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
    return { value: 'PLACEHOLDER' };
  }

  async openAppSettings(): Promise<void> {
    console.log('openAppSettings');
    return;
  }
}
