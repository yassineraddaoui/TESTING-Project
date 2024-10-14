import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BorrowedBookListPage } from './borrowed-book-list.page';

const routes: Routes = [
  {
    path: '',
    component: BorrowedBookListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BorrowedBookListPageRoutingModule {}
