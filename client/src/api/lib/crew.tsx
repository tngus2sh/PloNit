import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";
import * as Interfaces from "interface/crewInterface";

// 크루 생성
export async function getCrewCreate(
  accessToken: string,
  data: Interfaces.CrewInterface,
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
  const api = customApiForm("/plonit-service/v1/crew");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.post("", data).then(success).catch(fail);
}

// 크루 리스트 조회
export async function getCrewList(
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
  const api = customApi("/plonit-service/v1/crew");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("").then(success).catch(fail);
}

// 크루 상세 조회
export async function getCrewDetail(
  accessToken: string,
  crewId: number,
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
  const api = customApiForm("/plonit-service/v1/crew");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/${crewId}`).then(success).catch(fail);
}
