import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AlertController, MenuController, ToastController } from '@ionic/angular';
import { BookService } from 'src/app/services/book/book.service';
import { BorrowedBookListPageModule } from './borrowed-book-list.module';
import { BorrowedTransactionHistoryResponse } from 'src/app/models/authModels/BorrowedTransactionHistoryResponse';
import { FeedbackService } from 'src/app/services/feedback/feedback.service';
import { FeedbackRequest } from 'src/app/models/feedback/FeedBackRequest';

@Component({
  selector: 'app-borrowed-book-list',
  templateUrl: './borrowed-book-list.page.html',
  styleUrls: ['./borrowed-book-list.page.scss'],
})
export class BorrowedBookListPage implements OnInit {



  page:number=0
  size:number=14
  borrowedBookPageResponse:BorrowedBookListPageModule={}
  isLoading:boolean=false
  borrowedBooks:Array<BorrowedTransactionHistoryResponse>=[]
  selectedBook: BorrowedTransactionHistoryResponse|undefined=undefined
  feedbackRequest:FeedbackRequest={
    bookId: 0,
    comment: ''
  }
isEmpty: boolean=false;

  constructor(private menu:MenuController,private bookService:BookService,private alert:AlertController,
    private feedbackService:FeedbackService,
    private toastController:ToastController
  ) { }

  ngOnInit() {
    this.findAllBorrowedBook()

  }

  findAllBorrowedBook(event?:any){
    if(this.isLoading){
      return;
    }
    this.isLoading = true
    this.bookService.findAllBorrowedBook({page:this.page,size:this.size}).subscribe({
      next: res =>{
        this.borrowedBooks = [...this.borrowedBooks,...res.content || []]
        this.isEmpty = this.borrowedBooks.length === 0;
        this.isLoading = false
        if(event){
          event.target.complete()
        }
        if(this.page >= (res.totalPages as number) -1 && event){
          event.target.disabled = true
        }
      },
      error : async err =>{
        const alert = await this.alert.create({
          header: "Error",
          message: "Something went wrong",
          buttons: ['OK']
        })
        alert.present()
      }
    })
  }


  openMenu() {
   this.menu.open('first')
    }


    loadMore(event: Event) {
     this.page++
     this.findAllBorrowedBook(event)
      }


  returnBook(withFeedback: boolean) {
    this.bookService.returnBook(this.selectedBook?.id as number).subscribe({
      next: async res =>{
        if(withFeedback){
          this.saveFeedback()
          this.selectedBook = undefined
         


         } else{
          this.selectedBook = undefined
  
         }
      }
    })

    }


  private saveFeedback(){
    this.feedbackService.saveFeedback(this.feedbackRequest).subscribe({
      next:res=>{
        console.log("feedback gived succefully")
      }
    })
  }


  returnBackBoook(book: BorrowedTransactionHistoryResponse) {
    this.selectedBook = book
    this.feedbackRequest.bookId = book.id as number
    }

}
