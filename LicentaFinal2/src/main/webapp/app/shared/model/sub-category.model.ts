import { ICategory } from 'app/shared/model/category.model';

export interface ISubCategory {
  id?: number;
  name?: string;
  category?: ICategory;
}

export const defaultValue: Readonly<ISubCategory> = {};
