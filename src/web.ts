import { WebPlugin } from '@capacitor/core';

import type { DozeOptimizePlugin, IsIgnoringBatteryOptimizationsResult, IsIgnoringDataSaverResult, RequestDataSaverMenuResult, RequestOptimizationsMenuResult } from './definitions';

export class DozeOptimizeWeb extends WebPlugin implements DozeOptimizePlugin {
  async isIgnoringBatteryOptimizations(): Promise<IsIgnoringBatteryOptimizationsResult> {
    throw new Error('Method not implemented.');
  }
  async requestOptimizationsMenu(): Promise<RequestOptimizationsMenuResult> {
    throw new Error('Method not implemented.');
  }
  async isIgnoringDataSaver(): Promise<IsIgnoringDataSaverResult> {
    throw new Error('Method not implemented.');
  }
  async requestDataSaverMenu(): Promise<RequestDataSaverMenuResult> {
    throw new Error('Method not implemented.');
  }
}
