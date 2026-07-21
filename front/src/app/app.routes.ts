import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PlayListComponent } from './pages/play/play-list/play-list.component';
import { PlayFormComponent } from './pages/play/play-form/play-form.component';
import { GenreListComponent } from './pages/genre/genre-list/genre-list.component';
import { GenreFormComponent } from './pages/genre/genre-form/genre-form.component';
import { ScheduleListComponent } from './pages/schedule/schedule-list/schedule-list.component';
import { ScheduleFormComponent } from './pages/schedule/schedule-form/schedule-form.component';
import { PublicPurchaseComponent } from './pages/public-purchase.component';
import { TicketListComponent } from './pages/ticket/ticket-list/ticket-list.component';
import { TicketFormComponent } from './pages/ticket/ticket-form/ticket-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'comprar', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'comprar', component: PublicPurchaseComponent },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', component: DashboardComponent },
      { path: 'plays', component: PlayListComponent },
      { path: 'plays/new', component: PlayFormComponent },
      { path: 'plays/edit/:id', component: PlayFormComponent },
      { path: 'genres', component: GenreListComponent },
      { path: 'genres/new', component: GenreFormComponent },
      { path: 'genres/edit/:id', component: GenreFormComponent },
      { path: 'schedules', component: ScheduleListComponent },
      { path: 'schedules/new', component: ScheduleFormComponent },
      { path: 'schedules/edit/:id', component: ScheduleFormComponent },
      { path: 'tickets', component: TicketListComponent },
      { path: 'tickets/new', component: TicketFormComponent },
      { path: 'tickets/edit/:id', component: TicketFormComponent }
    ]
  },
  { path: '**', redirectTo: 'comprar' }
];
