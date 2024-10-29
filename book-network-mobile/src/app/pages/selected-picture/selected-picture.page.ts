import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { BookRequest } from 'src/app/models/bookModels/bookRequest';
import { BookService } from 'src/app/services/book/book.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-selected-picture',
  templateUrl: './selected-picture.page.html',
  styleUrls: ['./selected-picture.page.scss'],
})
export class SelectedPicturePage implements OnInit {


  book:BookRequest|null = null
  selectedPicture: string | undefined;
  selectedBookCover:any
  toastopen:boolean=false


  constructor(private sharedService:SharedService,private bookService:BookService,private toastController:ToastController
    ,private router:Router,private activateRoute:ActivatedRoute
  ) {
   }

  ngOnInit() {
    this.sharedService.currentBook.subscribe({
      next: bookRes =>{
        this.book = bookRes;
        this.selectedPicture = 'data:image/jpg;base64,'+this.book?.cover
      }
    })
    }



  async saveBook() {
    this.bookService.addBook(this.book).subscribe({
      next: (id) => {
        this.bookService.upploadBookCover(id, this.selectedBookCover).subscribe({
          next: async () => {
            const toast = await this.toastController.create({
              message: 'Book added successfully!',
              duration: 2000,
              color: 'success',
              position: 'bottom',
            });
            await toast.present();
            this.router.navigate(['my-book-list'])

          },
          error: async () => {
            const toast = await this.toastController.create({
              message: 'Failed to upload book cover.',
              duration: 2000,
              color: 'danger',
              position: 'bottom',
            });
            await toast.present();
          }
        });
      },
      error: async () => {
        const toast = await this.toastController.create({
          message: 'Failed to add book.',
          duration: 2000,
          color: 'danger',
          position: 'bottom',
        });
        await toast.present();
      }
    });
  }
  

  cancel() {

    this.router.navigate(['my-book-list'])
    }

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0]
    if (this.selectedBookCover){
      const reader = new FileReader()
      reader.onload = ()=>{
        this.selectedPicture = reader.result as string
      }
      reader.readAsDataURL(this.selectedBookCover)
    }

}}


