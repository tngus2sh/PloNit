export interface UserInterface {
  profileImage?: string;
  email?: string;
  nickname: string;
  name?: string;
  gender?: boolean;
  birth?: string;
  region?: string;
  dongCode?: number;
  height?: number;
  weight?: number;
  id1365?: string;
  ploggingCount?: number;
  crewCount?: number;
  badgeCount?: number;
}
export interface MyCrewpingInterface {
  id: number;
  crewName: string;
  crewpingName: string;
  crewpingImage: string;
  dday: number;
  startDate: string;
  endDate: string;
  place: string;
  cntPeople: number;
  memberProfileImage: any;
  isMaster: boolean;
}
