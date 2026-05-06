import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Cricketer } from '../../types/Cricketer';

@Component({
  selector: 'app-cricketer-edit',
  templateUrl: './cricketeredit.component.html',
  styleUrls: ['./cricketeredit.component.scss']
})
export class CricketerEditComponent implements OnInit {

  cricketerForm!: FormGroup;
  cricketerId!: number;

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.cricketerForm = this.fb.group({
      cricketerName: ['', Validators.required],
      age: ['', Validators.required],
      nationality: ['', Validators.required],
      experience: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  loadCricketerDetails(id: number): void {
    this.cricketerId = id;
    this.iplService.getCricketerById(id).subscribe(c => {
      this.cricketerForm.patchValue(c);
    });
  }

  onSubmit(): void {
    if (this.cricketerForm.invalid) return;

    const updated: Cricketer = {
      cricketerId: this.cricketerId,
      ...this.cricketerForm.value
    };

    this.iplService.updateCricketer(updated).subscribe();
  }
}