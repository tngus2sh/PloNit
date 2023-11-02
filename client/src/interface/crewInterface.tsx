import { AxiosHeaderValue } from "axios";

export interface CrewInterface {
  id: number;
  name: string;
  crewImage: string;
  region: string;
  introduce?: string;
  cntPeople?: number;
  rankinginfo?: string;
  totalRanking?: number;
  avgRanking?: number;
}
