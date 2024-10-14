import { Routes, RouterModule } from '@angular/router';
import { UpdateCarComponent as UpdateCarComponent } from './update-car.component';

const routes: Routes = [
  {  
    path:'',
    component: UpdateCarComponent
  },
];

export const UpdateCarRoutes = RouterModule.forChild(routes);
