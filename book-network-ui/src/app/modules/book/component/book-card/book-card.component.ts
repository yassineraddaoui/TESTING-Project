import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookResponse} from "../../../../services/models/book-response";


@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.css']
})
export class BookCardComponent {
  private _book: BookResponse={}
  private _book_Cover:string|undefined
  private _manage:boolean=false
  @Input()
  remove:boolean=false
  @Input()
  like:boolean=true
  get manage(): boolean {
    return this._manage;
  }
  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }


  get book_Cover(): string | undefined {
    if (this._book.cover){
      return 'data:image/jpg;base64, '+this._book.cover
    }
    return this._book_Cover;
  }

  set book_Cover(value: string | undefined) {
    this._book_Cover = value;
  }

  @Output() private share: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private archive: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private waitingList: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private borrow: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private edit: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private details: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()
  @Output() private removeItem: EventEmitter<BookResponse> = new EventEmitter<BookResponse>()

  onShowDetails() {
    this.details.emit(this._book)

  }

  onRemoveItem(){
    this.removeItem.emit(this._book)
  }

  onBorrow() {
    this.borrow.emit(this._book)

  }

  onAddToWaitingList() {
    this.waitingList.emit(this._book)

  }

  onEdit() {
    this.edit.emit(this._book)

  }

  onShare() {
   this.share.emit(this._book)
  }

  onArchive() {
    this.archive.emit(this._book)

  }
}
