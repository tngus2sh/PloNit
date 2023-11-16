export interface CrewInterface {
  id?: number;
  name: string;
  cntPeople?: number;
  crewImage: string;
  region: string;
  introduce?: string;
  totalRanking?: number;
  totalDistance?: number;
  avgRanking?: number;
  avgDistance?: number;
  crewMasterProfileImage?: string;
  crewMasterNickname?: string;
  notice?: string;
  isCrewMaster?: boolean;
  isMyCrew?: boolean;
  isWaiting?: boolean;
  startDate?: string;
  endDate?: string;
}

export interface FeedInterface {
  crewId?: number;
  id: number;
  nickname?: string;
  profileImage?: string;
  content?: string;
  feedPictures?: any;
  likeCount?: number;
  isLike?: boolean;
  isMine?: boolean;
  comments?: any;
  createdDate?: string;
}

export interface CrewpingInterface {
  crewpingId?: number;
  // id?: number;
  name: string;
  crewpingImage?: string;
  masterMemberId?: number;
  masterNickname?: string;
  masterImage?: string;
  place: string;
  startDate?: string;
  endDate?: string;
  cntPeople?: number;
  maxPeople?: number | string;
  introduce?: string;
  isJoined?: boolean;
}

export interface CommentInterface {
  feedId?: number;
  commentId?: number;
  nickname?: string;
  profileImage?: string;
  content?: string;
  isMine?: boolean;
}

export interface CrewAllowInterface {
  crewId?: number;
  commentId?: number;
  status?: boolean;
}

export interface MemberInterface {
  id?: number;
  crewMemberId?: number;
  profileImage: string;
  nickname: string;
}

export interface NoticeInterface {
  crewId: number;
  content: string;
}

export interface MemberListProps {
  title: string;
  location?: string;
  memberCount?: string;
  imageUrl: string; // 이미지 URL 추가
  showApproveButton?: boolean; // 승인 버튼 표시 여부를 위한 prop
  onApprove?: () => void; // 승인 버튼 클릭 시 호출할 함수
}
