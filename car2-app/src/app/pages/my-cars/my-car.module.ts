import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyCarComponent } from './my-car.component';
import { MyCarRoutes } from './my-car.routing';

@NgModule({
  imports: [
    CommonModule,
    MyCarRoutes
  ],
  declarations: [MyCarComponent]
})
export class MyCarModule { }
