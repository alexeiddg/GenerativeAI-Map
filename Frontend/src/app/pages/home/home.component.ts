import { Component } from '@angular/core';
import { HeroComponent } from "../../shared/components/hero/hero.component";
import { AIMapComponent } from "../../features/components/ai-map/ai-map.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeroComponent, AIMapComponent],
  templateUrl: './home.component.html',
  styles: ``
})
export class HomeComponent {

}
