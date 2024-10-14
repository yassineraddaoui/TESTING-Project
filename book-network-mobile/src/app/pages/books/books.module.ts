import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { BooksPageRoutingModule } from './books-routing.module';

import { BooksPage } from './books.page';

import { RatingComponent } from 'src/app/rating/rating.component';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BooksPageRoutingModule

],
  declarations: [BooksPage,RatingComponent],
  exports:[RatingComponent]
  
})
export class BooksPageModule {}
