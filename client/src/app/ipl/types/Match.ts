import { Team } from './Team';

export class Match {
  constructor(
    public matchId: number,
    public firstTeam: Team,
    public secondTeam: Team,
    public matchDate: Date,
    public venue: string,
    public result: string,
    public status: string,
    public winnerTeam: Team
  ) {}
}