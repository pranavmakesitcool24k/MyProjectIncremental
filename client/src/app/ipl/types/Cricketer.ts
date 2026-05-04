import { Team } from './Team';

export class Cricketer {
  constructor(
    public cricketerId: number,
    public cricketerName: string,
    public age: number,
    public nationality: string,
    public experience: number,
    public role: string,
    public totalRuns: number,
    public totalWickets: number,
    public team: Team
  ) {}
}