import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loading = signal(false);
  error = signal('');

  private fb = inject(FormBuilder);

  form = this.fb.group({
    userName: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set('');

    this.authService.login(this.form.getRawValue() as any).subscribe({
      next: () => this.router.navigate(['/admin']),
      error: (err) => {
        this.error.set(err.error?.message || 'Usuario o contraseña incorrectos');
        this.loading.set(false);
      }
    });
  }
}
