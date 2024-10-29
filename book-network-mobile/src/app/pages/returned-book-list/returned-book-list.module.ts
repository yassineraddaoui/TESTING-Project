import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ReturnedBookListPageRoutingModule } from './returned-book-list-routing.module';

import { ReturnedBookListPage } from './returned-book-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ReturnedBookListPageRoutingModule
  ],
  declarations: [ReturnedBookListPage]
})
export class ReturnedBookListPageModule {}
