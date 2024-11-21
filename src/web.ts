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
    return {
      value:
        '[\n  {\n    "score" : 0.40000000000000002,\n    "id" : "e6cb0440-5f58-5e18-a543-8a10911794f3",\n    "dataSources" : [\n      "age",\n      "gender",\n      "activity"\n    ],\n    "createdAtUtc" : "2024-11-21T00:04:11.827433+00:00",\n    "version" : 1,\n    "type" : "activity",\n    "factors" : [\n      {\n        "goal" : 10000,\n        "score" : 0.28000000000000003,\n        "unit" : "count",\n        "id" : "afb7b6e3-f15e-5872-addd-2f7e23cb2d39",\n        "value" : 142,\n        "name" : "steps",\n        "state" : "minimal"\n      },\n      {\n        "goal" : 500,\n        "score" : 0.11,\n        "unit" : "kcal",\n        "id" : "8abafec8-d7e8-516d-a94a-8d6c7a6949b1",\n        "value" : 1,\n        "name" : "active_calories",\n        "state" : "minimal"\n      },\n      {\n        "goal" : 10,\n        "score" : 1,\n        "unit" : "hour",\n        "id" : "bb63e675-19fc-539f-8844-088c5e6c7804",\n        "value" : 9,\n        "name" : "active_hours",\n        "state" : "high"\n      },\n      {\n        "goal" : 600,\n        "score" : 0.31,\n        "unit" : "minute",\n        "id" : "e2aecc53-a9cc-5ad8-a238-86b1f6b72d06",\n        "value" : null,\n        "name" : "extended_inactivity",\n        "state" : "minimal"\n      },\n      {\n        "goal" : 30,\n        "score" : 0.29999999999999999,\n        "unit" : "minute",\n        "id" : "fba9cda0-f5d9-515f-80cd-c1cb6b35e530",\n        "value" : 0,\n        "name" : "intense_activity_duration",\n        "state" : "minimal"\n      },\n      {\n        "goal" : 10,\n        "score" : null,\n        "unit" : "count",\n        "id" : "6ecc8f54-b28a-56ec-880e-beab453ca8d3",\n        "value" : null,\n        "name" : "floors_climbed",\n        "state" : null\n      }\n    ],\n    "state" : "minimal",\n    "scoreDateTime" : "2024-11-20T11:00:00+00:00"\n  }\n]',
    };
  }

  async openAppSettings(): Promise<void> {
    console.log('openAppSettings');
    return;
  }
}
