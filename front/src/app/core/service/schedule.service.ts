import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { PageResponse, Schedule, ScheduleRequest } from '../model/models';

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll(page: number, size: number) {
    return this.http.get<PageResponse<Schedule>>(`${this.apiUrl}/schedule/paginated?page=${page}&size=${size}`);
  }

  getById(id: number) {
    return this.http.get<Schedule>(`${this.apiUrl}/schedule/${id}`);
  }

  create(data: ScheduleRequest) {
    return this.http.post<Schedule>(`${this.apiUrl}/schedule`, data);
  }

  update(id: number, data: ScheduleRequest) {
    return this.http.put<Schedule>(`${this.apiUrl}/schedule/${id}`, data);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/schedule/${id}`);
  }
}
