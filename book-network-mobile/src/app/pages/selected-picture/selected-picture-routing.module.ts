import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SelectedPicturePage } from './selected-picture.page';

const routes: Routes = [
  {
    path: '',
    component: SelectedPicturePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SelectedPicturePageRoutingModule {}
