import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarsComponent } from './cars.component';
import { CarsRoutes } from './cars.routing';

@NgModule({
  imports: [
    CommonModule,
    CarsRoutes
  ],
  declarations: [CarsComponent]
})
export class CarsModule { }
