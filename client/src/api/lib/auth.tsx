import { AxiosResponse } from "axios";
import { customApi } from "./index";
import * as Interfaces from "interface/authInterface";

export async function getLogin(
  code: string,
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
    .get(`/kakao/login/${code}`)
    .then(success)
    .catch(fail);
}
