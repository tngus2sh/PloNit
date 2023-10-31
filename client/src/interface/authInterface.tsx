import { AxiosHeaderValue } from "axios";

export interface UserInterface {
  profileImg?: string;
  email?: string;
  nickname: string;
  name: string;
  gender: string;
  birth: string;
  region: string;
  height?: number;
  weight?: number;
  id_1365?: string;
}
