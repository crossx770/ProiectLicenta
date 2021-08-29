import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IJudet } from 'app/shared/model/judet.model';
import { ICity } from 'app/shared/model/city.model';
import { ICategory } from 'app/shared/model/category.model';
import { ISubCategory } from 'app/shared/model/sub-category.model';

export interface IPost {
  id?: number;
  title?: string;
  description?: string;
  is_promoted?: boolean | null;
  created_at?: string | null;
  price?: number;
  user_post?: IUser;
  judet?: string;
  city?: string;
  category?: string;
  subcategory?: string;
}

export const defaultValue: Readonly<IPost> = {
  is_promoted: false,
};
