import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { ActiveSchedule, PageResponse, Ticket, TicketPurchaseRequest, TicketRequest, User } from '../model/models';

@Injectable({ providedIn: 'root' })
export class TicketService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAll(page: number, size: number) {
    return this.http.get<PageResponse<Ticket>>(`${this.apiUrl}/ticket/paginated?page=${page}&size=${size}`);
  }

  getById(id: number) {
    return this.http.get<Ticket>(`${this.apiUrl}/ticket/${id}`);
  }

  create(data: TicketRequest) {
    return this.http.post<Ticket>(`${this.apiUrl}/ticket`, data);
  }

  purchase(data: TicketPurchaseRequest) {
    return this.http.post<Ticket>(`${this.apiUrl}/ticket/purchase`, data);
  }

  update(id: number, data: TicketRequest) {
    return this.http.put<Ticket>(`${this.apiUrl}/ticket/${id}`, data);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/ticket/${id}`);
  }

  getSchedules() {
    return this.http.get<ActiveSchedule[]>(`${this.apiUrl}/schedule/active`);
  }

  getUsers() {
    return this.http.get<User[]>(`${this.apiUrl}/user`);
  }
}
