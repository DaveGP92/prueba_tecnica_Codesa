import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { PlayService } from '../../../core/service/play.service';
import { Genre } from '../../../core/model/models';

@Component({
  selector: 'app-play-form',
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatIconModule],
  templateUrl: './play-form.component.html',
  styleUrl: './play-form.component.css'
})
export class PlayFormComponent implements OnInit {
  id = 0;
  isEdit = false;
  loading = false;
  error = '';
  genres: Genre[] = [];

  private fb = inject(FormBuilder);

  form = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(150), Validators.pattern(/^[a-zA-Z0-9áéíóúÁÉÍÓÚüÜñÑ\s.,:-]+$/)]],
    description: ['', [Validators.required, Validators.maxLength(1000)]],
    genreId: [null as number | null, [Validators.required]],
    durationMinutes: [null as number | null, [Validators.required, Validators.min(1), Validators.pattern(/^[0-9]+$/)]]
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private playService: PlayService
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.isEdit = !!this.id;
    this.loadGenres();

    if (this.isEdit) {
      this.loadPlay();
    }
  }

  loadGenres() {
    this.playService.getGenres().subscribe({
      next: (data) => this.genres = data,
      error: () => this.error = 'No se pudieron cargar los géneros'
    });
  }

  loadPlay() {
    this.loading = true;
    this.playService.getById(this.id).subscribe({
      next: (play) => {
        this.form.patchValue({
          title: play.title,
          description: play.description,
          genreId: play.genreId,
          durationMinutes: play.durationMinutes
        });
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
    const request = this.isEdit ? this.playService.update(this.id, data) : this.playService.create(data);

    request.subscribe({
      next: () => this.router.navigate(['/admin/plays']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo guardar la información';
        this.loading = false;
      }
    });
  }
}
