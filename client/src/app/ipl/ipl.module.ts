import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { IplRoutingModule } from './ipl-routing.module';
import { SharedModule } from '../shared/shared.module';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TeamCreateComponent } from './components/teamcreate/teamcreate.component';
import { CricketerCreateComponent } from './components/cricketercreate/cricketercreate.component';
import { MatchCreateComponent } from './components/matchcreate/matchcreate.component';
import { TicketBookingComponent } from './components/ticketbooking/ticketbooking.component';
import { VoteComponent } from './components/vote/vote.component';

@NgModule({
  declarations: [
    DashboardComponent,
    TeamCreateComponent,
    CricketerCreateComponent,
    MatchCreateComponent,
    TicketBookingComponent,
    VoteComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    IplRoutingModule,
    SharedModule
  ]
})
export class IplModule {}