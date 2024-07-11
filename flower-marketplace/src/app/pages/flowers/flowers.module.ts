import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlowersComponent } from './flowers.component';
import { FlowersRoutes } from './flowers.routing';

@NgModule({
  imports: [
    CommonModule,
    FlowersRoutes
  ],
  declarations: [FlowersComponent]
})
export class FlowersModule { }
