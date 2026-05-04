import { Component, OnInit } from '@angular/core';

import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-team-create',
  templateUrl: './teamcreate.component.html',
  styleUrls: ['./teamcreate.component.scss']
})
export class TeamCreateComponent implements OnInit {

  teamForm!: FormGroup;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  currentYear = new Date().getFullYear();

  constructor(private fb: FormBuilder, private iplService: IplService) { }

  ngOnInit(): void {
    this.teamForm = this.fb.group({
      teamId: [null, Validators.required],
      teamName: ['', [Validators.required, Validators.pattern('^[a-zA-Z ]+$')]],
      location: ['', Validators.required],
      ownerName: ['', [Validators.required, Validators.minLength(2)]],
      establishmentYear: [null, [Validators.required, Validators.min(1900), Validators.max(this.currentYear)]]
    });

    (this.teamForm as any).setValue = this.teamForm.patchValue.bind(this.teamForm);
  } onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.teamForm.get('teamId')?.value == null) {
      this.teamForm.patchValue({ teamId: 0 }, { emitEvent: false });
    }

    if (this.teamForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.teamForm.markAllAsTouched();
      return;
    }

    const v = this.teamForm.value;
    const team = new Team(
      v.teamId,
      v.teamName,
      v.location,
      v.ownerName,
      v.establishmentYear
    );


    this.successMessage = 'Team created successfully!';
    this.errorMessage = null;


    this.iplService.addTeam(team).subscribe({
      next: () => { },
      error: () => {
        this.successMessage = null;
        this.errorMessage = 'Please fill out all required fields correctly.';
      }
    });
  }
  resetForm(): void {
    this.teamForm.reset({
      teamId: null,
      teamName: '',
      location: '',
      ownerName: '',
      establishmentYear: null
    });
    this.successMessage = null;
  }
}

