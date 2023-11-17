import { AxiosResponse } from "axios";
import { customApi } from "./index";
import * as Interfaces from "interface/regionInterface";

// 개인 랭킹 조회
export async function getMemberRank(
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
  const api = customApi("/plonit-service/v1/rank");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("").then(success).catch(fail);
}

// 크루 전체 랭킹 조회
export async function getCrewTotalRank(
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
  const api = customApi("/plonit-service/v1/rank");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("/crew-total").then(success).catch(fail);
}

// 크루 평균 랭킹 조회
export async function getCrewAVGRank(
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
  const api = customApi("/plonit-service/v1/rank");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("/crew-avg").then(success).catch(fail);
}
