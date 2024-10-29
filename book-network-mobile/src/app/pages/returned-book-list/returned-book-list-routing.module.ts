import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ReturnedBookListPage } from './returned-book-list.page';

const routes: Routes = [
  {
    path: '',
    component: ReturnedBookListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ReturnedBookListPageRoutingModule {}
