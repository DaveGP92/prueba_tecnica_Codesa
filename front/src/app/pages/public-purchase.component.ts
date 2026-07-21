import { Component, OnInit, signal, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ActiveSchedule } from '../core/model/models';
import { PublicTicketService } from '../core/service/public-ticket.service';

@Component({
  selector: 'app-public-purchase',
  imports: [ReactiveFormsModule, MatButtonModule, MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule],
  templateUrl: './public-purchase.component.html',
  styleUrl: './public-purchase.component.css'
})
export class PublicPurchaseComponent implements OnInit {
  schedules = signal<ActiveSchedule[]>([]);
  error = signal('');
  message = signal('');
  loading = signal(false);

  private fb = inject(FormBuilder);

  form = this.fb.group({
    scheduleId: [null as number | null, [Validators.required]],
    quantity: [1, [Validators.required, Validators.min(1)]],
    customerName: ['', [Validators.required]],
    customerEmail: ['', [Validators.required, Validators.email]],
    customerPhone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]]
  });

  constructor(private publicTicketService: PublicTicketService) {}

  ngOnInit() {
    this.loadSchedules();
  }

  loadSchedules() {
    this.publicTicketService.getActiveSchedules().subscribe({
      next: (response) => this.schedules.set(response),
      error: (err) => this.error.set(err.error?.message || 'No se pudieron cargar las funciones')
    });
  }

  buy() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set('');
    this.message.set('');

    this.publicTicketService.purchase(this.form.getRawValue() as any).subscribe({
      next: (response) => {
        this.message.set(`Compra realizada. Ticket #${response.id}. Total: $${response.totalPrice}`);
        this.form.reset({ quantity: 1 });
        this.loadSchedules();
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set(err.error?.message || 'No se pudo realizar la compra');
        this.loading.set(false);
      }
    });
  }
}
