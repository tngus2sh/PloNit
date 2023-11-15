export interface RankInterface {
  rankingPeriod: string;
  rankingList: any;
}

export interface MemberRankInterface {
  nickname: string;
  profileImage: string;
  ranking: number;
  distance: number;
  isMine: boolean;
}

export interface CrewRankInterface {
  name: string;
  crewImage: string;
  ranking: number;
  distance: number;
  isMyCrew: boolean;
}

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
