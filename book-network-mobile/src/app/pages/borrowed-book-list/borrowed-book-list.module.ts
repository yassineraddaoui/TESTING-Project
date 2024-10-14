import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BorrowedBookListPageRoutingModule } from './borrowed-book-list-routing.module';

import { BorrowedBookListPage } from './borrowed-book-list.page';
import { RatingComponent } from 'src/app/rating/rating.component';
import { BooksPageModule } from '../books/books.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BorrowedBookListPageRoutingModule,
    BooksPageModule
  ],
  declarations: [BorrowedBookListPage]
 
})
export class BorrowedBookListPageModule {}
