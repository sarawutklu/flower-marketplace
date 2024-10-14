import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpdateFlowerComponent } from './update-flower.component';
import { UpdateFlowerRoutes } from './update-flower.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    UpdateFlowerRoutes,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [UpdateFlowerComponent]
})
export class UpdateFlowerModule { }
