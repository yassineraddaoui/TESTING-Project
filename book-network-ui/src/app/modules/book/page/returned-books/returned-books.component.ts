import {Component, OnInit} from '@angular/core';
import {
  PageResponseBorrowedTransactionHistoryResponse
} from "../../../../services/models/page-response-borrowed-transaction-history-response";
import {FeedbackRequest} from "../../../../services/models/feedback-request";
import {BorrowedTransactionHistoryResponse} from "../../../../services/models/borrowed-transaction-history-response";
import {BookService} from "../../../../services/services/book.service";
import {FeedbackService} from "../../../../services/services/feedback.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-returned-books',
  templateUrl: './returned-books.component.html',
  styleUrls: ['./returned-books.component.css']
})
export class ReturnedBooksComponent implements OnInit{

  returnedBooks: PageResponseBorrowedTransactionHistoryResponse ={};
  page:number=0
  size:number=10

  ;
  message: string=''
  level: string='success';

  constructor(private bookService:BookService,private toastService:ToastrService) {
  }




  ngOnInit(): void {
    this.findAllBorrowedBook()
  }

  private findAllBorrowedBook() {
    this.bookService.findReturnedBook({'page':this.page,'size':this.size})
      .subscribe({
        next:(bookPage)=>{
          this.returnedBooks = bookPage
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
    this.page = this.returnedBooks.totalPages as number - 1
    this.findAllBorrowedBook()
  }

  isLast() {
    return this.returnedBooks.totalPages as number - 1 == this.page
  }


  approveBookReturn(book: BorrowedTransactionHistoryResponse) {
    if (!book.returned){
      this.level = 'error'
      this.message ='Book is not yet approved'
      return
    }
    this.bookService.returnApprovedBook({'book-id':book.id as number})
      .subscribe({
        next:(res)=>{
          this.toastService.success("Book return approved","Done")
          this.findAllBorrowedBook()
        },
        error : (err)=>{
          this.toastService.error("you cannot approve the book that you are not own","Oups")
        }
      })
  }
}
