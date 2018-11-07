export interface IDimnesion {
  id?: string;
  length?: number;
  width?: number;
  height?: number;
  weight?: number;
}

export const defaultValue: Readonly<IDimnesion> = {};
