import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderModule } from './components/header/header.module';
import { UpdateCarModule } from './pages/update-car/update-car.module';
import { MyCarModule } from './pages/my-cars/my-car.module';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderModule,
    UpdateCarModule,
    MyCarModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'car2-app';
}
