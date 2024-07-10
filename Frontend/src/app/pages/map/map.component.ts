import { Component } from '@angular/core';
import { AIMapComponent } from "../../features/components/ai-map/ai-map.component";

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [
    AIMapComponent,
  ],
  templateUrl: './map.component.html',
  styles: ``
})
export class MapComponent {

}
