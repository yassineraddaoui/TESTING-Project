import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookRequest } from 'src/app/models/bookModels/bookRequest';
import { BookService } from 'src/app/services/book/book.service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.page.html',
  styleUrls: ['./manage-book.page.scss'],
})
export class ManageBookPage implements OnInit {
  book:BookRequest={}
  bookId:number=0


  constructor(private router:Router,private bookService:BookService, private sharedService:SharedService,private activateRoute:ActivatedRoute) {
  
   }

  ngOnInit() {
    this.bookId = this.activateRoute.snapshot.params['id']
    console.log("bookId",this.bookId)
    if (this.bookId){
      this.bookService.getBookById(this.bookId)
        .subscribe({
          next : (bookRes)=>{
            
            this.book = {
              id : bookRes.id,
              title: bookRes.title as string,
              authorName: bookRes.authorName as string,
              cover:bookRes.cover,
              isbn: bookRes.isbn as string,
              synopsis: bookRes.synopsis as string,
              shareable: bookRes.shareable

            }
           
          }
        })
    }
  }

  next() {

    this.sharedService.updateBook(this.book)
    this.router.navigate(['selected-picture'])

}

}
