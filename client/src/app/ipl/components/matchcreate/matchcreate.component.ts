import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Match } from '../../types/Match';

@Component({
  selector: 'app-match-create',
  templateUrl: './matchcreate.component.html',
  styleUrls: ['./matchcreate.component.scss']
})
export class MatchCreateComponent {

  matchForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  
  match: Match | null = null;

  constructor(private fb: FormBuilder) {
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
  }

  onSubmit(): void {
    if (this.matchForm.valid) {
      const v = this.matchForm.value;

    
      this.match = new Match(
        v.matchId,
        v.firstTeamId,
        v.secondTeamId,
        new Date(v.matchDate),
        v.venue,
        v.result,
        v.status,
        v.winnerTeamId
      );

      console.log(this.matchForm.value);

      
      this.successMessage = 'Match created successfully!';
      this.errorMessage = '';

      
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

    } else {
      this.errorMessage = 'Please fill all required fields';
      this.successMessage = '';
      this.matchForm.markAllAsTouched();
    }
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

    this.match = null;
    this.successMessage = '';
    this.errorMessage = '';
  }
}