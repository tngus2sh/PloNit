export interface RankInterface {
  startDate: string;
  endDate: string;
  membersRanks: any;
}

export interface RankDetailInterface {
  nickname?: string;
  crewImage?: string;
  profileImage?: string;
  ranking?: number;
  distance?: number;
  isMine?: boolean;
  isMyCrew?: boolean;
}

// export interface CrewRankInterface {
//   nickname: string;
//   crewImage: string;
//   ranking: number;
//   distance: number;
//   isMyCrew: boolean;
// }

export interface MyRankInterface {
  rank: number;
  distance: number;
  StartDate: string;
  EndDate: string;
}

export interface MyCrewRankInterface {
  totalRanking: number;
  avgRanking: number;
  totalDistance: number;
  avgDistance: number;
  startDate: string;
  endDate: string;
}
