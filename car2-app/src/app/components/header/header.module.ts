import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header.component';
import { HeaderRoutes } from './header.routing';

@NgModule({
  imports: [
    CommonModule,
    HeaderRoutes
  ],
  declarations: [HeaderComponent],
  exports: [
    HeaderComponent
  ],
})
export class HeaderModule { }
