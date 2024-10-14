import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadChildren: () => import('./pages/my-flower/my-flower.module').then(mod => mod.MyFlowerModule)
    },
    {
        path: 'flowers/:username',
        loadChildren: () => import('./pages/flowers/flowers.module').then(mod => mod.FlowersModule)
    },
    {
        path: 'add-flower',
        loadChildren: () => import('./pages/update-flower/update-flower.module').then(mod => mod.UpdateFlowerModule)
    },
    {
        path: 'update-flower/:id',
        loadChildren: () => import('./pages/update-flower/update-flower.module').then(mod => mod.UpdateFlowerModule)
    }
];
