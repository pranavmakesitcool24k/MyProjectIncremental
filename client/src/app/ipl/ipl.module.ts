import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { IplRoutingModule } from './ipl-routing.module';
import { SharedModule } from '../shared/shared.module';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TeamEditComponent } from './components/teamedit/teamedit.component';
import { CricketerEditComponent } from './components/cricketeredit/cricketeredit.component';
import { MatchEditComponent } from './components/matchedit/matchedit.component';

@NgModule({
  declarations: [
    DashboardComponent,
    TeamEditComponent,
    CricketerEditComponent,
    MatchEditComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    IplRoutingModule,
    SharedModule
  ]
})
export class IplModule {}