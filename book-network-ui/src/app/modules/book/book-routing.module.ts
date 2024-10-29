import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./page/main/main.component";
import {BookListComponent} from "./page/book-list/book-list.component";
import {MyBooksComponent} from "./page/my-books/my-books.component";
import {ManageBookComponent} from "./page/manage-book/manage-book.component";
import {BorrowedBookListComponent} from "./page/borrowed-book-list/borrowed-book-list.component";
import {ReturnedBooksComponent} from "./page/returned-books/returned-books.component";
import {guardAuthGuard} from "../../services/guard-auth.guard";
import {MyWaitingListComponent} from "./page/my-waiting-list/my-waiting-list.component";

const routes: Routes = [{
  path: '',
  component:MainComponent,
  canActivate:[guardAuthGuard],
  children: [
    {
      path:'',
      component:BookListComponent,
      canActivate:[guardAuthGuard]
    },{
      path:'my-books',
      component: MyBooksComponent,
      canActivate:[guardAuthGuard]
    },{
      path:'manage',
      component:ManageBookComponent,
      canActivate:[guardAuthGuard]
    },{
      path:'manage/:bookId',
      component:ManageBookComponent,
      canActivate:[guardAuthGuard]
    },{
      path:'my-borrowed-books',
      component:BorrowedBookListComponent,
      canActivate:[guardAuthGuard]
    },{
      path: 'my-returned-books',
      component: ReturnedBooksComponent,
      canActivate:[guardAuthGuard]
    },{
      path:'my-waiting-list',
      component:MyWaitingListComponent,
      canActivate:[guardAuthGuard]
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
