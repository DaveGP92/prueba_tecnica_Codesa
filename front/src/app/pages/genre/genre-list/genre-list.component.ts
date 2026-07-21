import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { GenreService } from '../../../core/service/genre.service';
import { Genre } from '../../../core/model/models';

@Component({
  selector: 'app-genre-list',
  imports: [RouterLink, MatButtonModule, MatIconModule, MatPaginatorModule, MatTableModule, MatProgressSpinnerModule],
  templateUrl: './genre-list.component.html',
  styleUrl: './genre-list.component.css'
})
export class GenreListComponent implements OnInit {
  displayedColumns = ['id', 'description', 'actions'];
  dataSource = new MatTableDataSource<Genre>([]);
  loading = signal(false);
  error = signal('');
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(private genreService: GenreService) {}

  ngOnInit() {
    this.loadGenres(this.pageIndex, this.pageSize);
  }

  loadGenres(page: number, size: number) {
    this.loading.set(true);
    this.error.set('');

    this.genreService.getAll(page, size).subscribe({
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
    this.loadGenres(event.pageIndex, event.pageSize);
  }

  deleteGenre(genre: Genre) {
    const ok = confirm(`¿Desea eliminar el género ${genre.description}?`);

    if (!ok) {
      return;
    }

    this.genreService.delete(genre.id).subscribe({
      next: () => this.loadGenres(this.pageIndex, this.pageSize),
      error: (err) => this.error.set(err.error?.message || 'No se pudo eliminar el registro')
    });
  }
}
