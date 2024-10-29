import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ManageBookPage } from './manage-book.page';



const routes: Routes = [
  {
    path: '',
    component: ManageBookPage
  },  {
    path: ':id', 
    component: ManageBookPage 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ManageBookPageRoutingModule {}
