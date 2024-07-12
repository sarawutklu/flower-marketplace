import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderModule } from './components/header/header.module';
import { UpdateFlowerModule } from './pages/update-flower/update-flower.module';
import { MyFlowerModule } from './pages/my-flower/my-flower.module';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderModule,
    UpdateFlowerModule,
    MyFlowerModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'flower-marketplace';
}
