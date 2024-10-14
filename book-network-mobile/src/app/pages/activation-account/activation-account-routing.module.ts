import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ActivationAccountPage } from './activation-account.page';

const routes: Routes = [
  {
    path: '',
    component: ActivationAccountPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ActivationAccountPageRoutingModule {}
