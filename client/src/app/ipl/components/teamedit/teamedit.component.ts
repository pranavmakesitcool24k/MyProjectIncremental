import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Team } from '../../types/Team';

@Component({
  selector: 'app-team-edit',
  templateUrl: './teamedit.component.html',
  styleUrls: ['./teamedit.component.scss']
})
export class TeamEditComponent implements OnInit {

  teamForm!: FormGroup;
  teamId!: number;

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.teamForm = this.fb.group({
      teamName: ['', Validators.required],
      location: ['', Validators.required],
      ownerName: ['', Validators.required],
      establishmentYear: ['', Validators.required]
    });
  }

  loadTeamDetails(id: number): void {
    this.teamId = id;
    this.iplService.getTeamById(id).subscribe(team => {
      this.teamForm.patchValue(team);
    });
  }

  onSubmit(): void {
    if (this.teamForm.invalid) return;

    const updatedTeam: Team = {
      teamId: this.teamId,
      ...this.teamForm.value
    };

    this.iplService.updateTeam(updatedTeam).subscribe();
  }
}