import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {}

  // ✅ REQUIRED BY TEST
  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]],
      password: ['', [Validators.required]]
    });
  }

  // ✅ REQUIRED BY TEST: do NOT call authService.login() when invalid
  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = '';
      this.loginForm.markAllAsTouched();
      return;
    }

    // ✅ REQUIRED BY TEST: call AuthService.login() with correct payload
    const payload = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    };

    this.authService.login(payload).subscribe({
      next: (res: any) => {
        // typical response contains token/roles/userId – store token at least
        if (res && res.token) localStorage.setItem('token', res.token);
        this.successMessage = 'Login successful';
        this.errorMessage = '';
      },
      error: (err: { error: { message: string; }; }) => {
        // backend error must propagate to UI
        this.errorMessage = err?.error?.message || 'Invalid username or password.';
        this.successMessage = '';
      }
    });
  }
}