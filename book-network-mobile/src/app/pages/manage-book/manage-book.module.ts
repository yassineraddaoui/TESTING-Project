import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ManageBookPageRoutingModule } from './manage-book-routing.module';

import { ManageBookPage } from './manage-book.page';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ManageBookPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ManageBookPage],


})
export class ManageBookPageModule {}
