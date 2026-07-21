import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from '../environment';
import { LoginRequest, LoginResponse } from '../model/models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  login(data: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, data).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
        localStorage.setItem('userName', response.userName);
        localStorage.setItem('role', response.role);
      })
    );
  }

  isLogged() {
    return !!localStorage.getItem('token');
  }

  getUserName() {
    return localStorage.getItem('userName') || 'Usuario';
  }

  getRole() {
    return localStorage.getItem('role') || '';
  }

  logout() {
    localStorage.clear();
  }
}
