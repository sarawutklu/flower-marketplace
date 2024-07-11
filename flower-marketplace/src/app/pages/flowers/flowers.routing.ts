import { Routes, RouterModule } from '@angular/router';
import { FlowersComponent } from './flowers.component';

const routes: Routes = [
  {  
    path:'',
    component:FlowersComponent
  },
];

export const FlowersRoutes = RouterModule.forChild(routes);
