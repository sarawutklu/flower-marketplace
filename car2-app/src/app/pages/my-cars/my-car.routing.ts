import { Routes, RouterModule } from '@angular/router';
import { MyCarComponent } from './my-car.component';

const routes: Routes = [
  { 
    path:'',
    component:MyCarComponent
  },
];

export const MyCarRoutes = RouterModule.forChild(routes);
