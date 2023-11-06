export interface CrewInterface {
  id?: number;
  name: string;
  crewImage: string;
  region: string;
  introduce?: string;
  cntPeople?: number;
  rankinginfo?: string;
  totalRanking?: number;
  avgRanking?: number;
}

export interface FeedInterface {
  content?: string;
  crewId?: number;
  feedPicture?: any;
}

export interface CrewpingInterface {
  id?: number;
  name: string;
  crewpingImage?: string;
  masterNickname?: string;
  masterImage?: string;
  place: string;
  startDate?: string;
  endDate?: string;
  cntPeople?: number;
  maxPeople?: number | string;
  introduce?: string;
}

export interface CommentInterface {
  feedId?: number;
  commentId?: number;
  nickname?: string;
  profileimage?: string;
  content?: string;
}
