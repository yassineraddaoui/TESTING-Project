import {Component, OnInit} from '@angular/core';
import {BorrowedTransactionHistoryResponse} from "../../../../services/models/borrowed-transaction-history-response";
import {
  PageResponseBorrowedTransactionHistoryResponse
} from "../../../../services/models/page-response-borrowed-transaction-history-response";
import {BookService} from "../../../../services/services/book.service";
import {borrowBook} from "../../../../services/fn/book/borrow-book";
import {FeedbackRequest} from "../../../../services/models/feedback-request";
import {FeedbackService} from "../../../../services/services/feedback.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-borrowed-book-list',
  templateUrl: './borrowed-book-list.component.html',
  styleUrls: ['./borrowed-book-list.component.css']
})
export class BorrowedBookListComponent implements OnInit{
  borrowedBook: PageResponseBorrowedTransactionHistoryResponse ={};
  page:number=0
  size:number=10
  feedbackRequest:FeedbackRequest={bookId: 0, comment: "",note:0}
  ;
  public selectedBook: BorrowedTransactionHistoryResponse | undefined= undefined;
  constructor(private bookService:BookService, private feedbackService:FeedbackService,
              private toastService:ToastrService) {
  }


  returnBorrowedBook(book: BorrowedTransactionHistoryResponse) {
    this.selectedBook = book
    this.feedbackRequest.bookId = book.id as number

  }

  ngOnInit(): void {
    this.findAllBorrowedBook()
  }

  private findAllBorrowedBook() {
    this.bookService.getBorrowedBook({'page':this.page,'size':this.size})
      .subscribe({
        next:(bookPage)=>{
          this.borrowedBook = bookPage
        }
      })
  }

  goToFirstPage() {
    this.page == 0
    this.findAllBorrowedBook()
  }

  previous() {
    this.page--
    this.findAllBorrowedBook()

  }

  goToPage(pageIndex: number) {
    this.page=pageIndex
    this.findAllBorrowedBook()

  }

  next() {
    this.page++
    this.findAllBorrowedBook()
  }

  goToLastPage() {
    this.page = this.borrowedBook.totalPages as number - 1
    this.findAllBorrowedBook()
  }

  isLast() {
    return this.borrowedBook.totalPages as number - 1 == this.page
  }

  returnBook(withFeedback: boolean) {
    this.bookService.returnBook({'book-id':this.selectedBook?.id as number})
      .subscribe({
      next : ()=>{
        this.toastService.success("Book returned successfully","success")
        if (withFeedback){
          this.giveFeedback()
          this.findAllBorrowedBook()
        }
        this.selectedBook=undefined
        this.findAllBorrowedBook()

      }

      })

  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({'body':this.feedbackRequest})
      .subscribe({
        next: ()=>{

        }
      })
  }
}
