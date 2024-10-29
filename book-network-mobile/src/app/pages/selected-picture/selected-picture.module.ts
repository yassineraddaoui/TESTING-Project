import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SelectedPicturePageRoutingModule } from './selected-picture-routing.module';

import { SelectedPicturePage } from './selected-picture.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SelectedPicturePageRoutingModule
  ],
  declarations: [SelectedPicturePage]
})
export class SelectedPicturePageModule {}
