import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";
import * as Interfaces from "interface/authInterface";

// 회원가입시 추가 정보
export async function addInfo(
  accessToken: string,
  data: Interfaces.UserInterface,
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
  const api = customApiForm("/plonit-service/v1/members");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.put("", data).then(success).catch(fail);
}

// 회원정보 수정
export async function EditProfile(
  accessToken: string,
  data: FormData,
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
  const api = customApiForm("/plonit-service/v1/members");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.put("", data).then(success).catch(fail);
}

// 회원 조회
export async function getProfile(
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
  const api = customApiForm("/plonit-service/v1/members");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get("").then(success).catch(fail);
}
