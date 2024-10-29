import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './page/main/main.component';
import { MenuComponent } from './component/menu/menu.component';
import { BookListComponent } from './page/book-list/book-list.component';
import { BookCardComponent } from './component/book-card/book-card.component';
import { RatingComponent } from './component/rating/rating.component';
import { MyBooksComponent } from './page/my-books/my-books.component';
import { ManageBookComponent } from './page/manage-book/manage-book.component';
import {FormsModule} from "@angular/forms";
import { BorrowedBookListComponent } from './page/borrowed-book-list/borrowed-book-list.component';
import { ReturnedBooksComponent } from './page/returned-books/returned-books.component';
import { MyWaitingListComponent } from './page/my-waiting-list/my-waiting-list.component';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    RatingComponent,
    MyBooksComponent,
    ManageBookComponent,
    BorrowedBookListComponent,
    ReturnedBooksComponent,
    MyWaitingListComponent
  ],
    imports: [
        CommonModule,
        BookRoutingModule,
        FormsModule
    ]
})
export class BookModule { }
