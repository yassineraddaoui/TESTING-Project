import {Component, OnInit} from '@angular/core';
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {shareBook} from "../../../../services/fn/book/share-book";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css']
})
export class MyBooksComponent implements OnInit {
  page: number = 0;
  size: number = 2;
  bookResponse: PageResponseBookResponse = {}


  constructor(private bookService: BookService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.findAllBooks()
  }


  private findAllBooks() {
    this.bookService.getBookByOwner({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (books) => {
        this.bookResponse = books

      }
    })
  }

  previous() {
    this.page--;
    this.findAllBooks()

  }


  goToPage(index: number) {
    this.page = index
    this.findAllBooks()

  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks()
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks()

  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1
    this.findAllBooks()

  }

  get isLastPage(): boolean {
    return this.page == this.bookResponse.totalPages as number - 1
  }


  archiveBook(book: BookResponse) {
    this.bookService.archivedBook({'book-id':book.id as number})
      .subscribe({
        next : ()=>{
          book.archived = !book.archived
        }
      })

  }



  editBook(book: BookResponse) {
    this.router.navigate(['/books/manage',book.id])

  }

  shareBook(book:BookResponse){
    this.bookService.shareBook({'book-id':book.id as number})
      .subscribe({
        next : () => {
          book.shareable = !book.shareable
        }
      })

  }
}
