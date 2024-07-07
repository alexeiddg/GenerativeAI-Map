import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { NavComponent } from "./shared/components/nav/nav.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent],
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'Generative AI Map';
}
