import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MyBookListPage } from './my-book-list.page';

const routes: Routes = [
  {
    path: '',
    component: MyBookListPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyBookListPageRoutingModule {}
