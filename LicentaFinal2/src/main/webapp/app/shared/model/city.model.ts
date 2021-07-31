import { IJudet } from 'app/shared/model/judet.model';

export interface ICity {
  id?: number;
  name?: string;
  judet?: IJudet;
}

export const defaultValue: Readonly<ICity> = {};
