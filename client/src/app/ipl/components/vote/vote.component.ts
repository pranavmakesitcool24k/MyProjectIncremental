import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { Cricketer } from '../../types/Cricketer';
import { Vote } from '../../types/Vote';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.scss']
})
export class VoteComponent implements OnInit {

  voteForm!: FormGroup;

  teams: Team[] = [];
  cricketers: Cricketer[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  vote: Vote | null = null;

  constructor(private fb: FormBuilder, private iplService: IplService) { }

  ngOnInit(): void {
    this.voteForm = this.fb.group({
      voteId: [null],
      email: ['', [Validators.required, Validators.email]],
      category: ['', Validators.required],


      team: [null],
      cricketer: [null],


      teamId: [null],
      cricketerId: [null]
    });
  }

  loadTeams(): void {
    this.iplService.getAllTeams().subscribe(data => this.teams = data || []);
  }

  loadCricketers(): void {
    this.iplService.getAllCricketers().subscribe(data => this.cricketers = data || []);
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.voteForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      return;
    }

    const v = this.voteForm.value;

    this.vote = new Vote(
      v.voteId,
      v.email,
      v.category,
      v.cricketer?.cricketerId ?? null,
      v.team?.teamId ?? null
    );

    this.iplService.createVote(this.vote).subscribe({
      next: () => {

        this.successMessage = 'Vote casted successfully!';
        this.errorMessage = null;
        this.voteForm.reset({
          voteId: null,
          email: '',
          category: '',
          team: null,
          cricketer: null
        });
      },
      error: () => {
        this.errorMessage = 'Please fill out all required fields correctly.';
        this.successMessage = null;
      }
    });
  }

  resetForm(): void {
    this.voteForm.reset({
      voteId: null,
      email: '',
      category: '',
      team: null,
      cricketer: null
    });
    this.vote = null;
    this.successMessage = null;
    this.errorMessage = null;
  }
}