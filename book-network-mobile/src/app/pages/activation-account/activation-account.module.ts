import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ActivationAccountPageRoutingModule } from './activation-account-routing.module';

import { ActivationAccountPage } from './activation-account.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ActivationAccountPageRoutingModule
  ],
  declarations: [ActivationAccountPage]
})
export class ActivationAccountPageModule {}
