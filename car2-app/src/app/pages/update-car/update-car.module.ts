import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpdateCarComponent as UpdateCarComponent } from './update-car.component';
import { UpdateCarRoutes } from './update-car.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    UpdateCarRoutes,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [UpdateCarComponent]
})
export class UpdateCarModule { }
