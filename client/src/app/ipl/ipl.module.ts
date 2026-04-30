import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { TeamCreateComponent } from './components/teamcreate/teamcreate.component';
import { CricketerCreateComponent } from './components/cricketercreate/cricketercreate.component';
import { MatchCreateComponent } from './components/matchcreate/matchcreate.component';
import { VoteComponent } from './components/vote/vote.component';
import { TicketBookingComponent } from './components/ticketbooking/ticketbooking.component';

@NgModule({
  declarations: [
    TeamCreateComponent,
    CricketerCreateComponent,
    MatchCreateComponent,
    VoteComponent,
    TicketBookingComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports: [
    TeamCreateComponent,
    CricketerCreateComponent,
    MatchCreateComponent,
    VoteComponent,
    TicketBookingComponent
  ]
})
export class IplModule {}