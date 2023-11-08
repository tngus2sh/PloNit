import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";

const itemApi = customApi(`/api/plogging-service/v1/item`);

const getBins = ({
  accessToken,
  latitude,
  longitude,
  success,
  fail,
}: {
  accessToken: string;
  latitude: number;
  longitude: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = itemApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/trashcan/${latitude}/${longitude}`).then(success).catch(fail);
};

const getToilets = ({
  accessToken,
  latitude,
  longitude,
  success,
  fail,
}: {
  accessToken: string;
  latitude: number;
  longitude: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = itemApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/toilet/${latitude}/${longitude}`).then(success).catch(fail);
};

export { getBins, getToilets };
