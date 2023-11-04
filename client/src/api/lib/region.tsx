import { AxiosResponse } from "axios";
import { customApi } from "./index";
import * as Interfaces from "interface/regionInterface";

export async function getSido(
  accessToken: string,
  success: (
    res: AxiosResponse<any, any>,
  ) =>
    | AxiosResponse<any, any>
    | PromiseLike<AxiosResponse<any, any>>
    | null
    | undefined
    | void,
  fail: (err: any) => PromiseLike<never> | null | undefined | void,
) {
  const api = customApi("/plonit-service/v1/region");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("/sido").then(success).catch(fail);
}

export async function getGugun(
  accessToken: string,
  sidocode: number,
  success: (
    res: AxiosResponse<any, any>,
  ) =>
    | AxiosResponse<any, any>
    | PromiseLike<AxiosResponse<any, any>>
    | null
    | undefined
    | void,
  fail: (err: any) => PromiseLike<never> | null | undefined | void,
) {
  const api = customApi("/plonit-service/v1/region");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/gugun/${sidocode}`).then(success).catch(fail);
}

export async function getDong(
  accessToken: string,
  guguncode: number,
  success: (
    res: AxiosResponse<any, any>,
  ) =>
    | AxiosResponse<any, any>
    | PromiseLike<AxiosResponse<any, any>>
    | null
    | undefined
    | void,
  fail: (err: any) => PromiseLike<never> | null | undefined | void,
) {
  const api = customApi("/plonit-service/v1/region");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/dong/${guguncode}`).then(success).catch(fail);
}
