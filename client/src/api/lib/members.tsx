import { AxiosResponse } from "axios";
import { customApi } from "./index";
import * as Interfaces from "interface/authInterface";

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
  const api = customApi("/plonit-service/v1/members");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.put("", data).then(success).catch(fail);
}
