import { WebPlugin } from '@capacitor/core';

import type { SahhaPlugin, SahhaSettings, SahhaDemographic, SahhaSensor, SahhaSensorStatus } from './definitions';

export class SahhaWeb extends WebPlugin implements SahhaPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('echo', options);
    return options;
  }

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
    return { demographic: `{ "gender" : "male", "age" : 40 }` };
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

  async openAppSettings(): Promise<void> {
    console.log('openAppSettings');
    return;
  }
}
