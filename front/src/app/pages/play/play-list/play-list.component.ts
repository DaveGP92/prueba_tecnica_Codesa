import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PlayService } from '../../../core/service/play.service';
import { Play } from '../../../core/model/models';

@Component({
  selector: 'app-play-list',
  imports: [RouterLink, MatButtonModule, MatIconModule, MatPaginatorModule, MatTableModule, MatProgressSpinnerModule],
  templateUrl: './play-list.component.html',
  styleUrl: './play-list.component.css'
})
export class PlayListComponent implements OnInit {
  displayedColumns = ['id', 'title', 'genreDescription', 'durationMinutes', 'active', 'actions'];
  dataSource = new MatTableDataSource<Play>([]);
  loading = signal(false);
  error = signal('');
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(private playService: PlayService) {}

  ngOnInit() {
    this.loadPlays(this.pageIndex, this.pageSize);
  }

  loadPlays(page: number, size: number) {
    this.loading.set(true);
    this.error.set('');

    this.playService.getAll(page, size).subscribe({
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
    this.loadPlays(event.pageIndex, event.pageSize);
  }

  deletePlay(play: Play) {
    const ok = confirm(`Desea eliminar la obra ${play.title}?`);

    if (!ok) {
      return;
    }

    this.playService.delete(play.id).subscribe({
      next: () => this.loadPlays(this.pageIndex, this.pageSize),
      error: (err) => this.error.set(err.error?.message || 'No se pudo eliminar el registro')
    });
  }
}
