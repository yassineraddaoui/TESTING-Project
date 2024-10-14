import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {BookResponse} from "../../../../services/models/book-response";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {ToastrModule, ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit{
  page: number=0;
  size: number=2;
  bookResponse:PageResponseBookResponse={}
  message: string='';
  level:string='success'

  constructor(private bookService:BookService,
              private router:Router, private toastService:ToastrService) {
  }

  ngOnInit(): void {
    this.findAllBooks()
  }


  private findAllBooks() {
    this.bookService.getBooks({
      page: this.page,
      size:this.size
    }).subscribe({next:(books)=>{
      this.bookResponse = books

      }})
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
    this.page = this.bookResponse.totalPages as number -1
    this.findAllBooks()

  }
  get isLastPage() :boolean{
    return this.page == this.bookResponse.totalPages as number -1
  }

  addToMyWaitingList(book: BookResponse) {
    this.bookService.addToWaitingList({'book-id':book.id as number})
      .subscribe({
        next : ()=>{
          this.toastService.success("Book is in the waiting list")
        },error: (err)=>{
          this.toastService.error(err.error.error,"something went wrong")
        }
      })
  }


  borrowBook(book: BookResponse) {
    this.bookService.borrowBook({"book-id":book.id as number}).subscribe({
    next : (res)=>{
      this.toastService.success("book borrowed successfully","Done !",{
        positionClass : "toast-bottom-full-width"
      })
    },
      error:(err)=>{
      this.toastService.error(err.error.error, "Oups !!",{
        positionClass: "toast-bottom-full-width"
      })
      }
    })
  }
}
