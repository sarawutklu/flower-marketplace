import { Routes, RouterModule } from '@angular/router';
import { MyFlowerComponent } from './my-flower.component';

const routes: Routes = [
  { 
    path:'',
    component:MyFlowerComponent
  },
];

export const MyFlowerRoutes = RouterModule.forChild(routes);
