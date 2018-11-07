import { Moment } from 'moment';

export interface IProduct {
  id?: string;
  brand?: string;
  name?: string;
  releaseDate?: Moment;
  comment?: string;
  manufactureOrigin?: string;
  meterials?: string;
  externalUrl?: string;
  originalPrice?: number;
  actualPrice?: number;
  garantie?: boolean;
  photo?: string;
  state?: string;
  creationDate?: Moment;
  modificationDate?: Moment;
}

export const defaultValue: Readonly<IProduct> = {
  garantie: false
};
