import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Ticket } from '../../../core/model/models';
import { TicketService } from '../../../core/service/ticket.service';

@Component({
  selector: 'app-ticket-list',
  imports: [RouterLink, MatButtonModule, MatIconModule, MatPaginatorModule, MatTableModule, MatProgressSpinnerModule],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.css'
})
export class TicketListComponent implements OnInit {
  displayedColumns = ['id', 'scheduleId', 'buyer', 'quantity', 'totalPrice', 'active', 'actions'];
  dataSource = new MatTableDataSource<Ticket>([]);
  loading = signal(false);
  error = signal('');
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.loadTickets(this.pageIndex, this.pageSize);
  }

  loadTickets(page: number, size: number) {
    this.loading.set(true);
    this.error.set('');

    this.ticketService.getAll(page, size).subscribe({
      next: (response) => {
        this.dataSource.data = response.content;
        this.totalElements = response.totalElements;
        this.pageIndex = response.number;
        this.pageSize = response.size;
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.error?.message || 'No se pudieron cargar las compras');
        this.loading.set(false);
      }
    });
  }

  changePage(event: PageEvent) {
    this.loadTickets(event.pageIndex, event.pageSize);
  }

  getBuyer(ticket: Ticket) {
    return ticket.userName || ticket.customerName || 'Sin comprador';
  }

  deleteTicket(ticket: Ticket) {
    const ok = confirm(`Desea eliminar la compra #${ticket.id}?`);

    if (!ok) {
      return;
    }

    this.ticketService.delete(ticket.id).subscribe({
      next: () => this.loadTickets(this.pageIndex, this.pageSize),
      error: (err) => this.error.set(err.error?.message || 'No se pudo eliminar la compra')
    });
  }
}
