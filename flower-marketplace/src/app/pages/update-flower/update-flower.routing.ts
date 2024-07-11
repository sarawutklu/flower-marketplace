import { Routes, RouterModule } from '@angular/router';
import { UpdateFlowerComponent } from './update-flower.component';

const routes: Routes = [
  {  
    path:'',
    component: UpdateFlowerComponent
  },
];

export const UpdateFlowerRoutes = RouterModule.forChild(routes);
