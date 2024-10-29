import { Component, OnInit } from '@angular/core';
import { BorrowedBookListPageModule } from '../borrowed-book-list/borrowed-book-list.module';
import { BorrowedTransactionHistoryResponse } from 'src/app/models/authModels/BorrowedTransactionHistoryResponse';
import { AlertController, MenuController, ToastController } from '@ionic/angular';
import { BookService } from 'src/app/services/book/book.service';

@Component({
  selector: 'app-returned-book-list',
  templateUrl: './returned-book-list.page.html',
  styleUrls: ['./returned-book-list.page.scss'],
})
export class ReturnedBookListPage implements OnInit {


  page:number=0
  size:number=14
  borrowedBookPageResponse:BorrowedBookListPageModule={}
  isLoading:boolean=false
  borrowedBooks:Array<BorrowedTransactionHistoryResponse>=[]

  constructor(private menu:MenuController,private bookService:BookService,private alert:AlertController,private toast:ToastController) { }

  ngOnInit() {
    this.findAllReturnedBook()

  }

  findAllReturnedBook(event?:any){
    if(this.isLoading){
      return;
    }
    this.isLoading = true
    this.bookService.findAllreturnedBook({page:this.page,size:this.size}).subscribe({
      next: res =>{
        this.borrowedBooks = [...this.borrowedBooks,...res.content || []]
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
     this.findAllReturnedBook(event)
      }

      ApprouveBook(book: BorrowedTransactionHistoryResponse) {
        this.bookService.approuvedBook(book.id as number).subscribe({
          next : async id =>{
           const toast = await this.toast.create({
            message: 'book was approuved succefully!',
              duration: 2000,
              color: 'success',
              position: 'bottom',
           })
           toast.present()
          },
          error: async err =>{
            const toast = await this.toast.create({
              message: 'something went wrong !',
                duration: 2000,
                color: 'danger',
                position: 'bottom',
             })
             toast.present()
          }
        })
        
        }

}
