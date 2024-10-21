import { registerPlugin } from '@capacitor/core';

import type { SahhaPlugin } from './definitions';

const Sahha = registerPlugin<SahhaPlugin>('Sahha', {
  web: () => import('./web').then((m) => new m.SahhaWeb()),
});

export * from './definitions';
export { Sahha };
