import { Moment } from 'moment';

export interface IToy {
  id?: string;
  recommendedAge?: string;
  gender?: string;
  purchaseDate?: Moment;
}

export const defaultValue: Readonly<IToy> = {};
