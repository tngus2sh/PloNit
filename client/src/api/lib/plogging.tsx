import { Axios, AxiosResponse } from "axios";
import { customApi, customApiForm } from "./index";
import { Coordinate } from "interface/ploggingInterface";

const ploggingApi = customApi(`/plogging-service/v1`);
const ploggingApiForm = customApiForm(`/plogging-service/v1`);

// 1. 플로깅 기록 저장
const savePlogging = ({
  accessToken,
  ploggingId,
  crewpingId,
  distance,
  calorie,
  review,
  people,
  coordinates,
  success,
  fail,
}: {
  accessToken: string;
  ploggingId?: number;
  crewpingId?: number;
  distance: number;
  calorie: number;
  review: string;
  people?: number | null;
  coordinates: Coordinate[];
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api
    .post(
      ``,
      JSON.stringify({
        ploggingId: ploggingId ?? null,
        crewpingId: crewpingId ?? null,
        distance: distance,
        calorie: calorie,
        review: review,
        people: people ?? null,
        coordinates: coordinates,
      }),
    )
    .then(success)
    .catch(fail);
};

// 플로깅 시작하기
const startPlogging = ({
  accessToken,
  type,
  latitude,
  longitude,
  crewpingId,
  success,
  fail,
}: {
  accessToken: string;
  type: "IND" | "VOL" | "CREWPING";
  latitude: number;
  longitude: number;
  crewpingId?: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api
    .post(
      `/start`,
      JSON.stringify({
        type: type,
        latitude: latitude,
        longitude: longitude,
        crewpingId: crewpingId,
      }),
    )
    .then(success)
    .catch(fail);
};

// 플로깅 기록 일별 조회
const searchPloggingUsingDay = ({
  accessToken,
  start_day,
  end_day,
  type,
  success,
  fail,
}: {
  accessToken: string;
  start_day: string;
  end_day: string;
  type: string | null;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api
    .get(`/period/${start_day}/${end_day}?type=${type}`)
    .then(success)
    .catch(fail);
};

// 플로깅 기록 월별 조회
const searchPloggingUsingMonth = ({
  accessToken,
  month,
  success,
  fail,
}: {
  accessToken: string;
  month: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/period?month=${month}`).then(success).catch(fail);
};

// 플로깅 기록 상세 조회
const searchPloggingInfo = ({
  accessToken,
  plogging_id,
  success,
  fail,
}: {
  accessToken: string;
  plogging_id: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/${plogging_id}`).then(success).catch(fail);
};

// 플로깅 도움 요청 저장
const saveHelp = ({
  accessToken,
  latitude,
  longitude,
  image,
  context,
  success,
  fail,
}: {
  accessToken: string;
  latitude: number;
  longitude: number;
  image?: File;
  context: string;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const formData = new FormData();
  formData.append("latitude", `${latitude}`);
  formData.append("longitude", `${longitude}`);
  if (image) {
    formData.append("image", image);
  }
  formData.append("context", context);

  const api = ploggingApiForm;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.post(`/help`, formData).then(success).catch(fail);
};

// 플로깅 도움 요청 지역별 조회
const searchHelpUsingLatLng = ({
  accessToken,
  latitude,
  longitude,
  success,
  fail,
}: {
  accessToken: string;
  latitude: number;
  longitude: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/help/${latitude}/${longitude}`).then(success).catch(fail);
};

// 플로깅 도움 요청 상태 변경
const changeHelp = ({
  accessToken,
  ploggingHelpId,
  isActive,
  success,
  fail,
}: {
  accessToken: string;
  ploggingHelpId: number;
  isActive: boolean;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api
    .patch(`/help`, JSON.stringify({ ploggingHelpId, isActive }))
    .then(success)
    .catch(fail);
};

// 플로깅 중 이미지 저장
const savePloggingImage = ({
  accessToken,
  plogging_id,
  image,
  success,
  fail,
}: {
  accessToken: string;
  plogging_id: number;
  image: File;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const formData = new FormData();
  formData.append("id", `${plogging_id}`);
  formData.append("image", image);

  const api = ploggingApiForm;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.post(`/image`, formData).then(success).catch(fail);
};

// 플로깅 주변의 유저 조회
const searchNeighbor = ({
  accessToken,
  latitude,
  longitude,
  success,
  fail,
}: {
  accessToken: string;
  latitude: number;
  longitude: number;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/users/${latitude}/${longitude}`).then(success).catch(fail);
};

// 내 플로깅 조회
export async function getMyPlogging(
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
  const api = customApi("/plonit-service/v1/flogging/{date}");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/${crewId}`).then(success).catch(fail);
}

// 내 플로깅 상세 조회
export async function getMyPloggingDetail(
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
  const api = customApi("/plonit-service/v1/flogging/{flogging-id}");
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  await api.get(`/${crewId}`).then(success).catch(fail);
}

// --- 미완성 인 것들 ---

// 봉사 정보 조회
const searchVolInfo = ({
  accessToken,
  success,
  fail,
}: {
  accessToken: string;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api.get(`/volunteer`).then(success).catch(fail);
};

// 봉사 정보 등록
const registerVolInfo = ({
  accessToken,
  ploggingId,
  name,
  phoneNumber,
  id1365,
  email,
  birth,
  success,
  fail,
}: {
  accessToken: string;
  ploggingId: number;
  name: string;
  phoneNumber: string;
  id1365: string;
  email: string;
  birth: string;
  success: (response: AxiosResponse<any, any>) => void | undefined;
  fail: (error: any) => void | undefined;
}) => {
  const api = ploggingApi;
  api.defaults.headers["accessToken"] = `Bearer ${accessToken}`;
  api
    .post(
      `/volunteer`,
      JSON.stringify({
        accessToken,
        ploggingId,
        name,
        phoneNumber,
        id1365,
        email,
        birth,
      }),
    )
    .then(success)
    .catch(fail);
};

export {
  savePlogging,
  startPlogging,
  searchPloggingUsingDay,
  searchPloggingUsingMonth,
  searchPloggingInfo,
  saveHelp,
  changeHelp,
  searchHelpUsingLatLng,
  savePloggingImage,
  searchNeighbor,
  searchVolInfo,
  registerVolInfo,
};
