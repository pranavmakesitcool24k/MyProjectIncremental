import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
//import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  registrationForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {}


  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      fullName: ['', Validators.required],
      username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern('^(?=.*[A-Z])(?=.*[0-9]).+$')
      ]]
    });
  }

  onSubmit(): void {
    if (this.registrationForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = '';
      this.registrationForm.markAllAsTouched();
      return;
    }

    this.authService.createUser(this.registrationForm.value).subscribe({
      next: (createdUser: any) => {
        this.successMessage = 'Registration successful!';
        this.errorMessage = '';
        console.log(createdUser);
        this.registrationForm.reset();
      },
      error: (err: { error: { message: string; }; }) => {
        this.errorMessage = err?.error?.message || 'Please fill out all required fields correctly.';
        this.successMessage = '';
      }
    });
  }

  resetForm(): void {
    this.registrationForm.reset();
    this.successMessage = '';
    this.errorMessage = '';
  }
}