import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationComponent, NotificationModalComponent } from './main';
import { HomeComponent } from "./main/home/home.component";
import { NavigationService } from './common-services';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationComponent, NotificationModalComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
constructor (nav:NavigationService){}
}

