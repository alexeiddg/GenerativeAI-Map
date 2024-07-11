import { Component } from '@angular/core';
import { HeroComponent } from "../../shared/components/hero/hero.component";
import { AIMapComponent } from "../../features/components/ai-map/ai-map.component";
import { StatsComponent } from "../../shared/ui/stats/stats.component";
import { CardSectionComponent } from "../../shared/components/card-section/card-section.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
      HeroComponent,
      AIMapComponent,
      StatsComponent,
      CardSectionComponent,
  ],
  templateUrl: './home.component.html',
  styles: ``
})
export class HomeComponent {

}
