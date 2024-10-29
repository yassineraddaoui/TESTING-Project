import {Component, OnInit} from '@angular/core';
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookService} from "../../../../services/services/book.service";
import {BookResponse} from "../../../../services/models/book-response";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-my-waiting-list',
  templateUrl: './my-waiting-list.component.html',
  styleUrls: ['./my-waiting-list.component.css']
})
export class MyWaitingListComponent implements OnInit{
  waitingListPageResponse: PageResponseBookResponse={}
  page:number=0
  size:number=2
  level:string='success'
  message:string=''
  constructor(private service:BookService,private toastService:ToastrService) {
  }
  private findMyList(){
    this.service.myBookList({'page':this.page,'size':this.size}).subscribe({
      next : (res)=>{
        this.waitingListPageResponse = res
      }
    })
  }

  ngOnInit(): void {
   this.findMyList()
  }


  goToFirstPage() {
    this.page=0
    this.findMyList()

  }

  previous() {
    this.page--
    this.findMyList()
  }

  goToPage(pageIndex:number) {
    this.page=pageIndex
    this.findMyList()

  }

  next() {
    this.page++
    this.findMyList()
  }

  isLast() {
    return this.page == this.waitingListPageResponse.totalPages as  number -1
  }

  goToLastPage() {
    this.page = this.waitingListPageResponse.totalPages as number - 1
    this.findMyList()

  }


    RemoveItemFromMyList(book: BookResponse) {
    this.service.removeBookFromWaitingList({"book-id":book.id as number})
      .subscribe({
        next : ()=>{
          this.toastService.success('Item was removed successfully',"Done")
          this.findMyList()
        },error : ()=>{
          this.toastService.error('book was not removed',"Oups !!")
        }
      })

    }
}
