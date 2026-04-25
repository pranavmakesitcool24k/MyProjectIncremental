import { Component } from '@angular/core';
import { Cricketer } from '../../types/Cricketer';

@Component({
  selector: 'app-cricketer-sample',
  standalone: true,
  templateUrl: './cricketersample.component.html',
  styleUrls: ['./cricketersample.component.scss']
})
export class CricketerSampleComponent {

  cricketer: Cricketer = new Cricketer(
    1,
    1,
    'Virat Kohli',
    35,
    'Indian',
    15,
    'Batsman',
    12000,
    4
  );

}