import { Component } from '@angular/core';

@Component({
  selector: 'stats',
  standalone: true,
  imports: [],
  templateUrl: './stats.component.html',
  styles: ``
})
export class StatsComponent {
  numTools: number = 5000;
  numCategories: number = 100;

}
