export interface CrewInterface {
  id?: number;
  name: string;
  cntPeople?: number;
  crewImage: string;
  region: string;
  introduce?: string;
  rankinginfo?: string;
  totalRanking?: number;
  avgRanking?: number;
  crewMasterProfileImage?: string;
  crewMasterNickname?: string;
  notice?: string;
}

export interface FeedInterface {
  crewId?: number;
  id: number;
  nickname?: string;
  profileImage?: string;
  content?: string;
  feedPictures?: any;
  likeCount?: number;
  islike?: boolean;
  isMine?: boolean;
  comments?: any;
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
  profileImage?: string;
  content?: string;
}
