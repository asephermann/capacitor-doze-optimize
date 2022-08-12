export interface DozeOptimizePlugin {
  isIgnoringBatteryOptimizations(): Promise<IsIgnoringBatteryOptimizationsResult>;
  requestOptimizationsMenu(): Promise<RequestOptimizationsMenuResult>;
  isIgnoringDataSaver(): Promise<IsIgnoringDataSaverResult>;
  requestDataSaverMenu(): Promise<RequestDataSaverMenuResult>;
}

export interface IsIgnoringBatteryOptimizationsResult {
  isIgnoring: boolean,
  messages?: string
}

export interface RequestOptimizationsMenuResult {
  isRequested: boolean,
  messages?: string
}

export interface IsIgnoringDataSaverResult {
  isIgnoring: boolean,
  messages?: string
}

export interface RequestDataSaverMenuResult {
  isRequested: boolean,
  messages?: string
}