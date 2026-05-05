import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';

@Component({
  selector: 'app-team-edit',
  template: '',  
})
export class TeamEditComponent implements OnInit {

 
  teamForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private iplService: IplService
  ) {}

  ngOnInit(): void {
    this.teamForm = this.fb.group({
      teamName: ['', Validators.required],
      location: ['', Validators.required],
      ownerName: ['', Validators.required],
      establishmentYear: ['', Validators.required]
    });
  }

 
  loadTeamDetails(teamId: number): void {
    this.iplService.getTeamById(teamId).subscribe((team: Team) => {
      this.teamForm.patchValue({
        teamName: team.teamName,
        location: team.location,
        ownerName: team.ownerName,
        establishmentYear: team.establishmentYear
      });
    });
  }

   
  onSubmit(): void {
    if (this.teamForm.invalid) {
      return;
    }

    const updatedTeam: Team = {
      teamId: 0 as any,  
      ...this.teamForm.value
    };

    this.iplService.updateTeam(updatedTeam).subscribe();
  }
}