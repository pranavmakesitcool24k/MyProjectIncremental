import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { Cricketer } from '../../types/Cricketer';
import { Match } from '../../types/Match';
import { TicketBooking } from '../../types/TicketBooking';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  teams: Team[] = [];
  cricketers: Cricketer[] = [];
  matches: Match[] = [];

  emailForm!: FormGroup;
  ticketsBooked: TicketBooking[] = [];

  constructor(private iplService: IplService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.emailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });

    this.loadTeams();
    this.loadCricketers();
    this.loadMatches();
  }
  loadAdminData(): void {
    this.loadTeams();
    this.loadCricketers();
    this.loadMatches();
  }

  loadTeams(): void {
    this.iplService.getAllTeams().subscribe(data => this.teams = data || []);
  }

  loadCricketers(): void {
    this.iplService.getAllCricketers().subscribe(data => this.cricketers = data || []);
  }

  loadMatches(): void {
    this.iplService.getAllMatches().subscribe(data => this.matches = data || []);
  }

  deleteTeam(teamId: number): void {
    const confirmDelete = window.confirm('Are you sure you want to delete this team?');

    if (!confirmDelete) {
      return;
    }

    this.iplService.deleteTeam(teamId).subscribe(() => {
      this.loadTeams();
    });
  }

  onSubmitEmail(): void {
    if (this.emailForm.invalid) return;

    const email = this.emailForm.value.email;
    this.iplService.getBookingsByUserEmail(email).subscribe(data => {
      this.ticketsBooked = data || [];
    });
  }
}