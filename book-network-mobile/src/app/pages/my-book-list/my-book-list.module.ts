import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { MyBookListPageRoutingModule } from './my-book-list-routing.module';

import { MyBookListPage } from './my-book-list.page';
import { BooksPageModule } from '../books/books.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    MyBookListPageRoutingModule,
    BooksPageModule
  ],
  declarations: [MyBookListPage]
})
export class MyBookListPageModule {}
