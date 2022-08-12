import { registerPlugin } from '@capacitor/core';

import type { DozeOptimizePlugin } from './definitions';

const DozeOptimize = registerPlugin<DozeOptimizePlugin>('DozeOptimize', {
  web: () => import('./web').then(m => new m.DozeOptimizeWeb()),
});

export * from './definitions';
export { DozeOptimize };
