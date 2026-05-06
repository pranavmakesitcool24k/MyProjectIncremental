import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Match } from '../../types/Match';

@Component({
  selector: 'app-match-edit',
  templateUrl: './matchedit.component.html',
  styleUrls: ['./matchedit.component.scss']
})
export class MatchEditComponent implements OnInit {

  matchForm!: FormGroup;
  matchId!: number;

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.matchForm = this.fb.group({
      venue: ['', Validators.required],
      result: ['', Validators.required],
      status: ['', Validators.required]
    });
  }

  loadMatchDetails(id: number): void {
    this.matchId = id;
    this.iplService.getMatchById(id).subscribe(m => {
      this.matchForm.patchValue(m);
    });
  }

  onSubmit(): void {
    if (this.matchForm.invalid) return;

    const updated: Match = {
      matchId: this.matchId,
      ...this.matchForm.value
    };

    this.iplService.updateMatch(updated).subscribe();
  }
}