import { Component } from '@angular/core';
import { Team } from '../../types/Team';

@Component({
  selector: 'app-team-sample',
  standalone: true,
  templateUrl: './teamsample.component.html',
  styleUrls: ['./teamsample.component.scss']
})
export class TeamSampleComponent {

  team: Team = new Team(
    1,
    'CSK',
    'Chennai',
    'India Cements',
    2008
  );

}
