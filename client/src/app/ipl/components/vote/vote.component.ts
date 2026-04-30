import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Vote } from '../../types/Vote';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.scss']
})
export class VoteComponent {

  voteForm: FormGroup;
  successMessage = '';
  errorMessage = '';

  vote: Vote | null = null;

  constructor(private fb: FormBuilder) {
    this.voteForm = this.fb.group({
      voteId: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      category: ['', Validators.required],
      cricketerId: [null, Validators.required],
      teamId: [null, Validators.required]
    });
  }

  onSubmit(): void {
    if (this.voteForm.valid) {
      const v = this.voteForm.value;

      this.vote = new Vote(
        v.voteId,
        v.email,
        v.category,
        v.cricketerId,
        v.teamId
      );

      console.log(this.voteForm.value);

     
      this.successMessage = 'Vote submitted successfully!';
      this.errorMessage = '';

      this.voteForm.reset({
        voteId: null,
        email: '',
        category: '',
        cricketerId: null,
        teamId: null
      });

    } else {
     
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = '';
      this.voteForm.markAllAsTouched();
    }
  }

  resetForm(): void {
    this.voteForm.reset({
      voteId: null,
      email: '',
      category: '',
      cricketerId: null,
      teamId: null
    });

    this.vote = null;
    this.successMessage = '';
    this.errorMessage = '';
  }
}