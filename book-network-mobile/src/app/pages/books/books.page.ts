import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AlertController, MenuController, ToastController } from '@ionic/angular';
import { IonInfiniteScrollCustomEvent } from '@ionic/core';
import { BookPageResponse } from 'src/app/models/bookModels/allBookPageResponse';
import { BookResponse } from 'src/app/models/bookModels/BookResponse';
import { BookService } from 'src/app/services/book/book.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.page.html',
  styleUrls: ['./books.page.scss'],
})
export class BooksPage implements OnInit {



  books:Array<BookResponse>=[]
  bookPageResponse:BookPageResponse={}
  page:number=0
  size:number=2
  isLoading:boolean=false
  bookCover: string | undefined;

  constructor(private bookService:BookService,
    private router:Router,
    private menu:MenuController,
    private alert:AlertController,
  private toast:ToastController) { }

  ngOnInit() {
    this.finBooks()
  }

  finBooks(event?:any){
    if(this.isLoading){
      return
    }
    this.isLoading = true
    this.bookService.findAllBooks({page:this.page,size:this.size}).subscribe({
      next: response =>{
        this.books = [...this.books,...response.content || []]
        this.isLoading = false
        if(event){
          event.target.complete()
        }

        if(this.page >= (response.totalPages as number) - 1 && event){
          event.target.disabled= true
        }

      },
      error: async err=>{
        const alert = await this.alert.create({
          header: 'Error',
          message: 'something went wrong',
          buttons: ['OK']
        })

        alert.present()
      }
    })

  }

  loadMore(event: IonInfiniteScrollCustomEvent<void>) {
    this.page++
    this.finBooks(event)
    }

    public selectedPicture(book: BookResponse): string | undefined {
      if (book.cover) {
        return 'data:image/jpg;base64, ' + book.cover;
      }
      return '';
    }
    

    menuOpen() {
      this.menu.open('first')
      }

    borrowBook(book: BookResponse) {
      this.bookService.borrowBook(book.id as number).subscribe({
        next : async res =>{
      
         const toast = await this.toast.create({
          message: "book has been borrowed succefully",
          duration:3000,
          color:"success",
          position:'bottom'

         })
         toast.present()
        },
        error: async err =>{
          const toast = await this.toast.create({
            message: err.error.error,
            duration:3000,
            color:"danger",
            position:'bottom'
  
           })
           toast.present()

        }
      })
      }

}
