import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { ActiveSchedule, Ticket, TicketPurchaseRequest } from '../model/models';

@Injectable({ providedIn: 'root' })
export class PublicTicketService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getActiveSchedules() {
    return this.http.get<ActiveSchedule[]>(`${this.apiUrl}/schedule/active`);
  }

  purchase(data: TicketPurchaseRequest) {
    return this.http.post<Ticket>(`${this.apiUrl}/ticket/purchase`, data);
  }
}
