import { AxiosResponse } from "axios";
import { customApi, customLoginApi } from "./index";
import * as Interfaces from "interface/authInterface";

// export async function login(
//   code: string,
//   success: (
//     res: AxiosResponse<any, any>,
//   ) =>
//     | AxiosResponse<any, any>
//     | PromiseLike<AxiosResponse<any, any>>
//     | null
//     | undefined
//     | void,
//   fail: (err: any) => PromiseLike<never> | null | undefined | void,
// ) {
//   await customApi("/plonit-service/auth")
//     .get(`/kakao/login/${code}`)
//     .then(success)
//     .catch(fail);
// }
export async function login(
  code: string,
  token: string | null,
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
  await customLoginApi("/plonit-service/auth", token || "") // token이 null일 경우 빈 문자열을 전달합니다.
    .get(`/kakao/login/${code}`)
    .then(success)
    .catch(fail);
}

export async function nicknameCheck(
  nickname: string,
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
  await customApi("/plonit-service/auth")
    .get(`/check-nickname/${nickname}`)
    .then(success)
    .catch(fail);
}

export async function logout(
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
  const api = customApi("/plonit-service/auth");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.post(`/kakao/logout`).then(success).catch(fail);
}
