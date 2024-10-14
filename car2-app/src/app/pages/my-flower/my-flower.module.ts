import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyFlowerComponent } from './my-flower.component';
import { MyFlowerRoutes } from './my-flower.routing';

@NgModule({
  imports: [
    CommonModule,
    MyFlowerRoutes
  ],
  declarations: [MyFlowerComponent]
})
export class MyFlowerModule { }
