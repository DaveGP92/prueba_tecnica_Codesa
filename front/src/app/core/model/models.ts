export interface LoginRequest {
  userName: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
  userId: number;
  userName: string;
  role: string;
}

export interface Play {
  id: number;
  title: string;
  description: string;
  genreId: number;
  genreDescription: string;
  durationMinutes: number;
  active: boolean;
  createdAt: string;
}

export interface PlayRequest {
  title: string;
  description: string;
  genreId: number;
  durationMinutes: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface Genre {
  id: number;
  description: string;
}

export interface GenreRequest {
  description: string;
}

export interface ActiveSchedule {
  scheduleId: number;
  dateTime: string;
  room: string;
  availableSeats: string;
  basePrice: string;
  playId: number;
  playTitle: string;
  playDescription: string;
  durationMinutes: number;
  genreId: number;
  genreDescription: string;
}

export interface TicketPurchaseRequest {
  scheduleId: number;
  userId?: number;
  customerName?: string;
  customerEmail?: string;
  customerPhone?: string;
  quantity: number;
}

export interface TicketRequest {
  scheduleId: number;
  userId?: number;
  customerName?: string;
  customerEmail?: string;
  customerPhone?: string;
  quantity: number;
  totalPrice: number;
}

export interface Ticket {
  id: number;
  scheduleId: number;
  userId?: number;
  userName?: string;
  customerName?: string;
  customerEmail?: string;
  customerPhone?: string;
  quantity: number;
  totalPrice: number;
  active: boolean;
  createdAt: string;
}

export interface User {
  id: number;
  userName: string;
  email: string;
  fullName: string;
  role: string;
  createdAt: string;
}

export interface Schedule {
  id: number;
  playId: number;
  playTitle: string;
  dateTime: string;
  room: string;
  totalSeats: string;
  availableSeats: string;
  basePrice: string;
  active: boolean;
  createdAt: string;
}

export interface ScheduleRequest {
  playId: number;
  dateTime: string;
  room: string;
  totalSeats: string;
  availableSeats: string;
  basePrice: string;
}
