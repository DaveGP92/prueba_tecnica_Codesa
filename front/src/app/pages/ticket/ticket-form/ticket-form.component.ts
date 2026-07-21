import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { ActiveSchedule, User } from '../../../core/model/models';
import { TicketService } from '../../../core/service/ticket.service';

@Component({
  selector: 'app-ticket-form',
  imports: [RouterLink, ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatIconModule],
  templateUrl: './ticket-form.component.html',
  styleUrl: './ticket-form.component.css'
})
export class TicketFormComponent implements OnInit {
  id = 0;
  isEdit = false;
  loading = false;
  error = '';
  schedules: ActiveSchedule[] = [];
  users: User[] = [];
  buyerTypes = [
    { value: 'guest', label: 'Comprador invitado' },
    { value: 'user', label: 'Usuario registrado' }
  ];

  private fb = inject(FormBuilder);

  form = this.fb.group({
    scheduleId: [null as number | null, [Validators.required]],
    buyerType: ['guest', [Validators.required]],
    userId: [null as number | null],
    customerName: [''],
    customerEmail: [''],
    customerPhone: ['', [Validators.pattern(/^[0-9]{10}$/)]],
    quantity: [1, [Validators.required, Validators.min(1)]]
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService
  ) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.isEdit = !!this.id;
    this.loadData();

    if (this.isEdit) {
      this.loadTicket();
    }
  }

  loadData() {
    this.ticketService.getSchedules().subscribe({
      next: (data) => this.schedules = data,
      error: () => this.error = 'No se pudieron cargar las funciones'
    });

    this.ticketService.getUsers().subscribe({
      next: (data) => this.users = data,
      error: () => this.error = 'No se pudieron cargar los usuarios'
    });
  }

  loadTicket() {
    this.loading = true;
    this.ticketService.getById(this.id).subscribe({
      next: (ticket) => {
        this.form.patchValue({
          scheduleId: ticket.scheduleId,
          buyerType: ticket.userId ? 'user' : 'guest',
          userId: ticket.userId || null,
          customerName: ticket.customerName || '',
          customerEmail: ticket.customerEmail || '',
          customerPhone: ticket.customerPhone || '',
          quantity: ticket.quantity
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'No se pudo cargar la compra';
        this.loading = false;
      }
    });
  }

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const data = this.form.getRawValue();

    if (data.buyerType === 'user' && !data.userId) {
      this.error = 'Seleccione un usuario';
      return;
    }

    if (data.buyerType === 'guest' && (!data.customerName || !data.customerEmail || !data.customerPhone)) {
      this.error = 'Complete los datos del comprador';
      return;
    }

    this.loading = true;
    this.error = '';

    if (this.isEdit) {
      this.updateTicket();
      return;
    }

    if (data.buyerType === 'user') {
      this.createTicketForUser();
    } else {
      this.createTicketForGuest();
    }
  }

  createTicketForUser() {
    const data = this.form.getRawValue();
    const totalPrice = this.calculateTotalPrice();

    this.ticketService.create({
      scheduleId: data.scheduleId!,
      userId: data.userId!,
      quantity: data.quantity!,
      totalPrice
    }).subscribe({
      next: () => this.router.navigate(['/admin/tickets']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo guardar la compra';
        this.loading = false;
      }
    });
  }

  createTicketForGuest() {
    const data = this.form.getRawValue();

    this.ticketService.purchase({
      scheduleId: data.scheduleId!,
      quantity: data.quantity!,
      customerName: data.customerName!,
      customerEmail: data.customerEmail!,
      customerPhone: data.customerPhone!
    }).subscribe({
      next: () => this.router.navigate(['/admin/tickets']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo guardar la compra';
        this.loading = false;
      }
    });
  }

  updateTicket() {
    const data = this.form.getRawValue();
    const totalPrice = this.calculateTotalPrice();

    this.ticketService.update(this.id, {
      scheduleId: data.scheduleId!,
      userId: data.buyerType === 'user' ? data.userId! : undefined,
      customerName: data.buyerType === 'guest' ? data.customerName! : undefined,
      customerEmail: data.buyerType === 'guest' ? data.customerEmail! : undefined,
      customerPhone: data.buyerType === 'guest' ? data.customerPhone! : undefined,
      quantity: data.quantity!,
      totalPrice
    }).subscribe({
      next: () => this.router.navigate(['/admin/tickets']),
      error: (err) => {
        this.error = err.error?.message || 'No se pudo actualizar la compra';
        this.loading = false;
      }
    });
  }

  calculateTotalPrice() {
    const data = this.form.getRawValue();
    const schedule = this.schedules.find((item) => item.scheduleId === data.scheduleId);
    const basePrice = Number(schedule?.basePrice || 0);

    return basePrice * Number(data.quantity || 0);
  }
}
