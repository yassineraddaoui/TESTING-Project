import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseRoute } from 'src/app/Helper/BaseRoute';
import { BookPageResponse, PageParams } from 'src/app/models/bookModels/allBookPageResponse';
import { BookRequest } from 'src/app/models/bookModels/bookRequest';
import { BookResponse } from 'src/app/models/bookModels/BookResponse';
import { MyBoorowedBookPageResponse } from 'src/app/models/bookModels/BorrowedBookList';
import { MyBookPageResponse } from 'src/app/models/bookModels/myBookPageResponse';
import { BorrowedBookListPage } from 'src/app/pages/borrowed-book-list/borrowed-book-list.page';

@Injectable({
  providedIn: 'root'
})
export class BookService {


  private readonly allBooksUrl:string="/books"
  private readonly myBookUrl:string="/books/owner"
  private readonly archiveBookUrl:string="/books/archived/"
  private readonly shareBookUrl:string="/books/shareable/"
  private readonly borrowedBookListUrl:string="/books/borrowed"
  private readonly returnedBookListUrl:string="/books/returned"
  private readonly borrowBooktUrl:string="/books/borrow/"
  private readonly returnedBookUrl:string="/books/borrow/return/"
  private readonly saveBooksUrl:string="/books"
  private readonly upploadFilesUrl:string="/books/cover/"
  private readonly getBookByIdUrl: String="/books/"
  private readonly approuvedBookUrl: string ='/books/borrow/return/approved/'


  constructor(private http:HttpClient) { }

  findAllBooks(param:PageParams){
    return this.http.get<BookPageResponse>(`${BaseRoute.rootUrl}${this.allBooksUrl }`,{params:{...param}})
  }

  findMyBooks(param:PageParams){
    return this.http.get<MyBookPageResponse>(`${BaseRoute.rootUrl}${this.myBookUrl }`,{params:{...param}})
  }
  archivedBook(bookId:number){
    return this.http.patch<number>(`${BaseRoute.rootUrl}${this.archiveBookUrl}${bookId}`,{})
  }

shareBook(bookId:number){
  return this.http.patch<number>(`${BaseRoute.rootUrl}${this.shareBookUrl}${bookId}`,{})
}

findAllBorrowedBook(param:PageParams){
  return this.http.get<MyBoorowedBookPageResponse>(`${BaseRoute.rootUrl}${this.borrowedBookListUrl}`,{params:{...param}})
}

findAllreturnedBook(param:PageParams){
  return this.http.get<MyBoorowedBookPageResponse>(`${BaseRoute.rootUrl}${this.returnedBookListUrl}`,{params:{...param}})
}
borrowBook(bookId: number) {
  return this.http.post<number>(`${BaseRoute.rootUrl}${this.borrowBooktUrl}${bookId}`,{})
}

returnBook(bookId:number){
  return this.http.patch<number>(`${BaseRoute.rootUrl}${this.returnedBookUrl}${bookId}`,{})
}

addBook(bookRequest:BookRequest | null){
  return this.http.post<number>(`${BaseRoute.rootUrl}${this.saveBooksUrl}`,bookRequest)
}

upploadBookCover(bookId:number,file:File){
  const formData= new FormData();
  formData.append('file',file)
  return this.http.post(`${BaseRoute.rootUrl}${this.upploadFilesUrl}${bookId}`,formData)
}

getBookById(bookId:number) {
 return this.http.get<BookResponse>(`${BaseRoute.rootUrl}${this.getBookByIdUrl}${bookId}`)
}

approuvedBook(bookId:number){
  return this.http.patch<number>(`${BaseRoute.rootUrl}${this.approuvedBookUrl}${bookId}`,{})
}

}

