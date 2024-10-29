import { Component, OnInit,Input } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss'],
})
export class RatingComponent  implements OnInit {
  private _rating :number=0
  maxRating: number =5

  constructor() { }

  ngOnInit() {}


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
