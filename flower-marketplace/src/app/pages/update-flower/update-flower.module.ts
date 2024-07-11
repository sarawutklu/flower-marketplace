import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpdateFlowerComponent } from './update-flower.component';
import { UpdateFlowerRoutes } from './update-flower.routing';

@NgModule({
  imports: [
    CommonModule,
    UpdateFlowerRoutes
  ],
  declarations: [UpdateFlowerComponent]
})
export class UpdateFlowerModule { }
