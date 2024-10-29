import {Component, Input} from '@angular/core';
import {BookResponse} from "../../../../services/models/book-response";


@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent {
 private _rating :number=0
  maxRating: number =5
  @Input()
  set rating(value: number) {
    this._rating = value;
  }



  get fullStars(){
    return Math.floor(this._rating)
  }

  get halfStar(){
    return this._rating % 1 !== 0
  }
  get emptyStars() {
    return this.maxRating - Math.ceil(this._rating)
  }

}
