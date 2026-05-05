import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IplService } from '../../services/ipl.service';
import { Match } from '../../types/Match';
import { TicketBooking } from '../../types/TicketBooking';

@Component({
  selector: 'app-ticket-booking',
  templateUrl: './ticketbooking.component.html',
  styleUrls: ['./ticketbooking.component.scss']
})
export class TicketBookingComponent implements OnInit {
 
  matches: Match[] = [];
  ticketBookingForm!: FormGroup;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  ticketBooking: TicketBooking | null = null;

  constructor(private fb: FormBuilder, private iplService: IplService) {}

  ngOnInit(): void {
    this.ticketBookingForm = this.fb.group({
     
      bookingId: [null],
      email: ['', [Validators.required, Validators.email]],
     
      match: [null, Validators.required],
      numberOfTickets: [null, [Validators.required, Validators.min(1)]]
    });

    this.loadMatches();
  }
 
  loadMatches(): void {
    this.iplService.getAllMatches().subscribe(data => {
      this.matches = (data || []) as Match[];
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.ticketBookingForm.invalid) {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = null;
      this.ticketBookingForm.markAllAsTouched();
      return;
    }

    const v = this.ticketBookingForm.value;
    const selectedMatch: any = v.match;

    const payload: TicketBooking = new TicketBooking(
      v.bookingId,
      v.email,
      selectedMatch.matchId,
      v.numberOfTickets
    );

    this.ticketBooking = payload;

    this.iplService.createBooking(payload).subscribe({
      next: () => {
        this.successMessage = 'Ticket booked successfully!';
        this.errorMessage = null;
        this.ticketBookingForm.reset({ bookingId: null, email: '', match: null, numberOfTickets: null });
      },
      error: () => {
        this.errorMessage = 'Please fill out all required fields correctly.';
        this.successMessage = null;
      }
    });
  }

  resetForm(): void {
    this.ticketBookingForm.reset({ bookingId: null, email: '', match: null, numberOfTickets: null });
    this.ticketBooking = null;
    this.successMessage = null;
    this.errorMessage = null;
  }
}