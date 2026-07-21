import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Genre, GenreRequest, PageResponse } from '../model/models';

@Injectable({ providedIn: 'root' })
export class GenreService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll(page: number, size: number) {
    return this.http.get<PageResponse<Genre>>(`${this.apiUrl}/genre/paginated?page=${page}&size=${size}`);
  }

  getById(id: number) {
    return this.http.get<Genre>(`${this.apiUrl}/genre/${id}`);
  }

  create(data: GenreRequest) {
    return this.http.post<Genre>(`${this.apiUrl}/genre`, data);
  }

  update(id: number, data: GenreRequest) {
    return this.http.put<Genre>(`${this.apiUrl}/genre/${id}`, data);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/genre/${id}`);
  }
}
