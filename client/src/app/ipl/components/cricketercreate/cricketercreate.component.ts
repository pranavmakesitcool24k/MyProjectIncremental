import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { Cricketer } from '../../types/Cricketer';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cricketer-create',
  templateUrl: './cricketercreate.component.html',
  styleUrls: ['./cricketercreate.component.scss']
})
export class CricketerCreateComponent implements OnInit {

  cricketerForm!: FormGroup;
  cricketer: Cricketer | null = null;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  teams: Team[] = [];

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.cricketerForm = this.fb.group({
      cricketerId: [null, Validators.required],
      teamId: [null, Validators.required],
      cricketerName: ['', Validators.required],
      age: [null, Validators.required],
      nationality: ['', Validators.required],
      experience: [0, [Validators.required, Validators.min(0)]],
      role: ['', Validators.required],
      totalRuns: [null, Validators.required],
      totalWickets: [null, Validators.required]
    });

    this.loadTeams();
  }

  loadTeams(): void {
    this.iplService.getAllTeams().subscribe({
      next: (data) => this.teams = data,
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.cricketerForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.cricketerForm.markAllAsTouched();
      return;
    }

    const v = this.cricketerForm.value;
    const teamObj = new Team(v.teamId, '', '', '', 0);

    const payload: any = {
      cricketerId: v.cricketerId,
      team: teamObj,
      cricketerName: v.cricketerName,
      age: v.age,
      nationality: v.nationality,
      experience: v.experience,
      role: v.role,
      totalRuns: v.totalRuns,
      totalWickets: v.totalWickets
    };

    this.iplService.addCricketer(payload).subscribe({
      next: (created) => {
        this.cricketer = created as any;
        this.successMessage = 'Cricketer created successfully!';
        this.cricketerForm.reset({ experience: 0, teamId: null, cricketerId: null });
      },
      error: (err: HttpErrorResponse) => this.handleError(err)
    });
  }

  handleError(error: HttpErrorResponse): void {
    this.errorMessage = error?.error?.message || 'Server error occurred.';
  }
}
