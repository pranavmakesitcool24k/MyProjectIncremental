import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { Match } from '../../types/Match';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-match-create',
  templateUrl: './matchcreate.component.html',
  styleUrls: ['./matchcreate.component.scss']
})
export class MatchCreateComponent implements OnInit {

  matchForm!: FormGroup;
  match: Match | null = null;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  teams: Team[] = [];

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.matchForm = this.fb.group({
      matchId: [null, Validators.required],
      firstTeamId: [null, Validators.required],
      secondTeamId: [null, Validators.required],
      matchDate: ['', Validators.required],
      venue: ['', Validators.required],
      result: ['', Validators.required],
      status: ['', Validators.required],
      winnerTeamId: [null, Validators.required]
    });

    this.loadTeams();
  }

  loadTeams(): void {
    this.iplService.getAllTeams().subscribe({
      next: data => this.teams = data,
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.matchForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.matchForm.markAllAsTouched();
      return;
    }

    this.addMatch();
  }

  addMatch(): void {
    const v = this.matchForm.value;

    const payload: any = {
      matchId: v.matchId,
      firstTeam: new Team(v.firstTeamId, '', '', '', 0),
      secondTeam: new Team(v.secondTeamId, '', '', '', 0),
      matchDate: new Date(v.matchDate),
      venue: v.venue,
      result: v.result,
      status: v.status,
      winnerTeam: new Team(v.winnerTeamId, '', '', '', 0)
    };

    this.iplService.addMatch(payload).subscribe({
      next: (created) => {
        this.match = created as any;
        this.successMessage = 'Match created successfully!';
        this.resetForm();
      },
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  resetForm(): void {
    this.matchForm.reset({
      matchId: null,
      firstTeamId: null,
      secondTeamId: null,
      matchDate: '',
      venue: '',
      result: '',
      status: '',
      winnerTeamId: null
    });
  }

  handleError(error: HttpErrorResponse): void {
    this.errorMessage = error?.error?.message || 'Server error occurred.';
  }
}