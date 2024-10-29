import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { BookRequest } from '../models/bookModels/bookRequest';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private bookSource = new BehaviorSubject<BookRequest | null>(null);
  currentBook = this.bookSource.asObservable();

  updateBook(book:BookRequest){
    this.bookSource.next(book)
  }
}
