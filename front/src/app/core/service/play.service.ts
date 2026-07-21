import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Genre, PageResponse, Play, PlayRequest } from '../model/models';

@Injectable({ providedIn: 'root' })
export class PlayService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll(page: number, size: number) {
    return this.http.get<PageResponse<Play>>(`${this.apiUrl}/play/paginated?page=${page}&size=${size}`);
  }

  getById(id: number) {
    return this.http.get<Play>(`${this.apiUrl}/play/${id}`);
  }

  create(data: PlayRequest) {
    return this.http.post<Play>(`${this.apiUrl}/play`, data);
  }

  update(id: number, data: PlayRequest) {
    return this.http.put<Play>(`${this.apiUrl}/play/${id}`, data);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/play/${id}`);
  }

  getGenres() {
    return this.http.get<Genre[]>(`${this.apiUrl}/genre`);
  }
}
