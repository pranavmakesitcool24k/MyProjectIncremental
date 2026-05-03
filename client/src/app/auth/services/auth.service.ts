import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  private baseUrl(): string {
    return (typeof window !== 'undefined' && window.location && window.location.href)
      ? window.location.href
      : '';
  }

  login(user: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl()}/user/login`, user);
  }

  createUser(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl()}/user/register`, user);
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }
}