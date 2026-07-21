import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ScheduleService } from '../../../core/service/schedule.service';
import { Schedule } from '../../../core/model/models';

@Component({
  selector: 'app-schedule-list',
  imports: [RouterLink, MatButtonModule, MatIconModule, MatPaginatorModule, MatTableModule, MatProgressSpinnerModule],
  templateUrl: './schedule-list.component.html',
  styleUrl: './schedule-list.component.css'
})
export class ScheduleListComponent implements OnInit {
  displayedColumns = ['id', 'playTitle', 'dateTime', 'room', 'totalSeats', 'availableSeats', 'basePrice', 'active', 'actions'];
  dataSource = new MatTableDataSource<Schedule>([]);
  loading = signal(false);
  error = signal('');
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(private scheduleService: ScheduleService) {}

  ngOnInit() {
    this.loadSchedules(this.pageIndex, this.pageSize);
  }

  loadSchedules(page: number, size: number) {
    this.loading.set(true);
    this.error.set('');

    this.scheduleService.getAll(page, size).subscribe({
      next: (response) => {
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
        this.pageIndex = response.number;
        this.pageSize = response.size;
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.error?.message || 'No se pudo cargar la información');
        this.loading.set(false);
      }
    });
  }

  changePage(event: PageEvent) {
    this.loadSchedules(event.pageIndex, event.pageSize);
  }

  deleteSchedule(schedule: Schedule) {
    const ok = confirm(`¿Desea eliminar la función de ${schedule.playTitle}?`);

    if (!ok) {
      return;
    }

    this.scheduleService.delete(schedule.id).subscribe({
      next: () => this.loadSchedules(this.pageIndex, this.pageSize),
      error: (err) => this.error.set(err.error?.message || 'No se pudo eliminar el registro')
    });
  }
}
