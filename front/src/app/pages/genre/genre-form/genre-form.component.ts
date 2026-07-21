import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { GenreService } from '../../../core/service/genre.service';

@Component({
  selector: 'app-genre-form',
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './genre-form.component.html',
  styleUrl: './genre-form.component.css'
})
export class GenreFormComponent implements OnInit {
  id = 0;
  isEdit = false;
  loading = false;
  error = '';

  private fb = inject(FormBuilder);

  form = this.fb.group({
    description: ['', [Validators.required, Validators.maxLength(100)]]
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private genreService: GenreService
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.isEdit = !!this.id;

    if (this.isEdit) {
      this.loadGenre();
    }
  }

  loadGenre() {
    this.loading = true;
    this.genreService.getById(this.id).subscribe({
      next: (genre) => {
        this.form.patchValue({ description: genre.description });
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'No se pudo cargar el registro';
        this.loading = false;
      }
    });
  }

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.error = '';
    const data = this.form.getRawValue() as any;
    const request = this.isEdit ? this.genreService.update(this.id, data) : this.genreService.create(data);

    request.subscribe({
      next: () => this.router.navigate(['/admin/genres']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo guardar la información';
        this.loading = false;
      }
    });
  }
}
