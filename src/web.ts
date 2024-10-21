import { WebPlugin } from '@capacitor/core';

import type { SahhaPlugin } from './definitions';

export class SahhaWeb extends WebPlugin implements SahhaPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
