import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";

// 피드 생성
export async function getFeedCreate(
  accessToken: string,
  formData: FormData,
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
  const api = customApiForm("/plonit-service/v1/feed");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.post("", formData).then(success).catch(fail);
}

// 피드 조회
export async function getFeedList(
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
  const api = customApi("/plonit-service/v1/feed");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`${crewId}`).then(success).catch(fail);
}
