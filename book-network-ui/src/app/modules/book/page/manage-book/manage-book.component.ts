import {Component, OnInit} from '@angular/core';
import {BookRequest} from "../../../../services/models/book-request";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.css']
})
export class ManageBookComponent implements OnInit{
  errorMsg:Array<string>=[]
  selectedPicture: string | undefined;
  selectedBookCover:any
  bookRequest: BookRequest = {authorName: "", isbn: "", synopsis: "", title: "",shareable:false};
  constructor(private bookService:BookService,private router:Router
              ,private activateRoute:ActivatedRoute
  , private toastService:ToastrService) {
  }

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0]
    console.log(this.selectedBookCover)
    if (this.selectedBookCover){
      const reader = new FileReader()
      reader.onload = ()=>{
        this.selectedPicture = reader.result as string
      }
      reader.readAsDataURL(this.selectedBookCover)
    }


  }

  saveBook() {
    this.bookService.saveBook({ body: this.bookRequest }).subscribe({
      next: (bookId) => {
        this.bookService.uploadBookCoverPicture({ "book-id": bookId, body: { file: this.selectedBookCover } })
          .subscribe({
            next: () => {
              this.toastService.success("Book information has been successfully saved", "Done");
              this.router.navigate(['/books/my-books']);
            }
          });
        console.log(this.bookRequest.shareable);
      },
      error: (err) => {
        this.toastService.error("something went wrong","Oups !!")
        this.errorMsg = err.error.validationErrors;
      }
    });
  }


  ngOnInit(): void {
    const bookId = this.activateRoute.snapshot.params['bookId']
    if (bookId){
      this.bookService.getBookById({'book-id':bookId})
        .subscribe({
          next : (book)=>{
            this.bookRequest = {
              id : book.id,
              title: book.title as string,
              authorName: book.authorName as string,
              isbn: book.isbn as string,
              synopsis: book.synopsis as string,
              shareable: book.shareable
            }
            if (book.cover){
              this.selectedPicture = 'data:image/jpg;base64,'+book.cover
            }
          }
        })
    }
  }

}
