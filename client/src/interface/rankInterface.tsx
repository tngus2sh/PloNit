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
