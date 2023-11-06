import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";
import * as Interfaces from "interface/crewInterface";

// 크루 생성
export async function getCrewpingCreate(
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
  const api = customApiForm("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.post("", formData).then(success).catch(fail);
}
