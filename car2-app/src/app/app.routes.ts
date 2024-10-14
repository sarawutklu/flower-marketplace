import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadChildren: () => import('./pages/my-cars/my-car.module').then(mod => mod.MyCarModule)
    },
    {
        path: 'flowers/:username',
        loadChildren: () => import('./pages/cars/cars.module').then(mod => mod.CarsModule)
    },
    {
        path: 'add-flower',
        loadChildren: () => import('./pages/update-car/update-car.module').then(mod => mod.UpdateCarModule)
    },
    {
        path: 'update-flower/:id',
        loadChildren: () => import('./pages/update-car/update-car.module').then(mod => mod.UpdateCarModule)
    }
];
