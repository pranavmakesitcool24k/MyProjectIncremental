import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeamCreateComponent } from './components/teamcreate/teamcreate.component';
import { CricketerCreateComponent } from './components/cricketercreate/cricketercreate.component';
import { MatchCreateComponent } from './components/matchcreate/matchcreate.component';

const routes: Routes = [
  { path: 'team/create', component: TeamCreateComponent },
  { path: 'cricketer/create', component: CricketerCreateComponent },
  { path: 'match/create', component: MatchCreateComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IplRoutingModule {}
