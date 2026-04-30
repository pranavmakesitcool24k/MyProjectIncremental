import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TicketBooking } from '../../types/TicketBooking';

@Component({
  selector: 'app-ticket-booking',
  templateUrl: './ticketbooking.component.html',
  styleUrls: ['./ticketbooking.component.scss']
})
export class TicketBookingComponent {


  ticketBookingForm: FormGroup;

  successMessage = '';
  errorMessage = '';

  ticketBooking: TicketBooking | null = null;

  constructor(private fb: FormBuilder) {
    this.ticketBookingForm = this.fb.group({
      bookingId: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      matchId: [null, Validators.required],
      numberOfTickets: [null, [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit(): void {
    if (this.ticketBookingForm.valid) {
      const v = this.ticketBookingForm.value;

      this.ticketBooking = new TicketBooking(
        v.bookingId,
        v.email,
        v.matchId,
        v.numberOfTickets
      );

      console.log(this.ticketBookingForm.value);

      
      this.successMessage = 'Tickets booked successfully!';
      this.errorMessage = '';

      this.ticketBookingForm.reset({
        bookingId: null,
        email: '',
        matchId: null,
        numberOfTickets: null
      });

    } else {
    
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = '';
      this.ticketBookingForm.markAllAsTouched();
    }
  }

  resetForm(): void {
    this.ticketBookingForm.reset({
      bookingId: null,
      email: '',
      matchId: null,
      numberOfTickets: null
    });

    this.ticketBooking = null;
    this.successMessage = '';
    this.errorMessage = '';
  }
}