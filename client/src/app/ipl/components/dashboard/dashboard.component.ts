import { Component, OnInit } from '@angular/core';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { Cricketer } from '../../types/Cricketer';
import { Match } from '../../types/Match';
import { Vote } from '../../types/Vote';
import { TicketBooking } from '../../types/TicketBooking';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  role: string | null = null;

  teams: Team[] = [];
  cricketers: Cricketer[] = [];
  matches: Match[] = [];
  votes: Vote[] = [];
  bookings: TicketBooking[] = [];

  constructor(private iplService: IplService) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
    this.loadAdminData();
  }

  loadAdminData(): void {
    this.iplService.getAllTeams().subscribe(t => this.teams = t || []);
    this.iplService.getAllCricketers().subscribe(c => this.cricketers = c || []);
    this.iplService.getAllMatches().subscribe(m => this.matches = m || []);
    this.iplService.getAllVotes().subscribe(v => this.votes = v || []);
    this.iplService.getAllTicketBookings().subscribe(b => this.bookings = b || []);
  }

  deleteTeam(id: number): void {
    if (window.confirm('Confirm delete team?')) {
      this.iplService.deleteTeam(id).subscribe(() => this.loadAdminData());
    }
  }

  deleteCricketer(id: number): void {
    if (window.confirm('Confirm delete cricketer?')) {
      this.iplService.deleteCricketer(id).subscribe(() => this.loadAdminData());
    }
  }

  deleteMatch(id: number): void {
    if (window.confirm('Confirm delete match?')) {
      this.iplService.deleteMatch(id).subscribe(() => this.loadAdminData());
    }
  }
}