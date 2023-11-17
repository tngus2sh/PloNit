import { AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";
import * as Interfaces from "interface/crewInterface";

// 크루핑 생성
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

// 크루핑 목록 조회
export async function getCrewpingList(
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/${crewId}`).then(success).catch(fail);
}

// 크루핑 상세 조회
export async function getCrewpingInfo(
  accessToken: string,
  crewpingId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/detail/${crewpingId}`).then(success).catch(fail);
}

// 크루핑 참가 요청
export async function getCrewpingJoin(
  accessToken: string,
  crewpingId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.post(`/join/${crewpingId}`).then(success).catch(fail);
}

// 크루핑 멤버 조회 (크루장)
export async function getCrewpingMemberListMaster(
  accessToken: string,
  crewpingId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/master-member/${crewpingId}`).then(success).catch(fail);
}

// 크루핑 멤버 조회 (크루원)
export async function getCrewpingMemberList(
  accessToken: string,
  crewpingId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/member/${crewpingId}`).then(success).catch(fail);
}

// 크루핑 참가 취소
export async function getCrewpingQuit(
  accessToken: string,
  crewpingId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.delete(`/quit/${crewpingId}`).then(success).catch(fail);
}

// 크루핑 강퇴
export async function getCrewpingKickOut(
  accessToken: string,
  crewpingId: number,
  memberId: number,
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
  const api = customApi("/plonit-service/v1/crewping");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api
    .delete(`/kick-out/${crewpingId}/${memberId}`)
    .then(success)
    .catch(fail);
}
