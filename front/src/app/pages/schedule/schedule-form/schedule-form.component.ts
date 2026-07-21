import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { ScheduleService } from '../../../core/service/schedule.service';
import { PlayService } from '../../../core/service/play.service';
import { Play } from '../../../core/model/models';

@Component({
  selector: 'app-schedule-form',
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatIconModule],
  templateUrl: './schedule-form.component.html',
  styleUrl: './schedule-form.component.css'
})
export class ScheduleFormComponent implements OnInit {
  id = 0;
  isEdit = false;
  loading = false;
  error = '';
  plays: Play[] = [];

  private fb = inject(FormBuilder);

  form = this.fb.group({
    playId: [null as number | null, [Validators.required]],
    dateTime: ['', [Validators.required]],
    room: ['', [Validators.required, Validators.maxLength(100)]],
    totalSeats: ['', [Validators.required]],
    availableSeats: ['', [Validators.required]],
    basePrice: ['', [Validators.required]]
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
    private playService: PlayService
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.isEdit = !!this.id;
    this.loadPlays();

    if (this.isEdit) {
      this.loadSchedule();
    }
  }

  loadPlays() {
    this.playService.getAll(0, 100).subscribe({
      next: (response) => this.plays = response.content,
      error: () => this.error = 'No se pudieron cargar las obras'
    });
  }

  loadSchedule() {
    this.loading = true;
    this.scheduleService.getById(this.id).subscribe({
      next: (schedule) => {
        this.form.patchValue({
          playId: schedule.playId,
          dateTime: schedule.dateTime,
          room: schedule.room,
          totalSeats: schedule.totalSeats,
          availableSeats: schedule.availableSeats,
          basePrice: schedule.basePrice
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
    const request = this.isEdit ? this.scheduleService.update(this.id, data) : this.scheduleService.create(data);

    request.subscribe({
      next: () => this.router.navigate(['/admin/schedules']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo guardar la información';
        this.loading = false;
      }
    });
  }
}
