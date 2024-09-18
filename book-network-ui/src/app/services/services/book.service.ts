/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { archivedBook } from '../fn/book/archived-book';
import { ArchivedBook$Params } from '../fn/book/archived-book';
import { BookResponse } from '../models/book-response';
import { borrowBook } from '../fn/book/borrow-book';
import { BorrowBook$Params } from '../fn/book/borrow-book';
import { findReturnedBook } from '../fn/book/find-returned-book';
import { FindReturnedBook$Params } from '../fn/book/find-returned-book';
import { getBookById } from '../fn/book/get-book-by-id';
import { GetBookById$Params } from '../fn/book/get-book-by-id';
import { getBookByOwner } from '../fn/book/get-book-by-owner';
import { GetBookByOwner$Params } from '../fn/book/get-book-by-owner';
import { getBooks } from '../fn/book/get-books';
import { GetBooks$Params } from '../fn/book/get-books';
import { getBorrowedBook } from '../fn/book/get-borrowed-book';
import { GetBorrowedBook$Params } from '../fn/book/get-borrowed-book';
import { PageResponseBookResponse } from '../models/page-response-book-response';
import { PageResponseBorrowedTransactionHistoryResponse } from '../models/page-response-borrowed-transaction-history-response';
import { returnApprovedBook } from '../fn/book/return-approved-book';
import { ReturnApprovedBook$Params } from '../fn/book/return-approved-book';
import { returnBook } from '../fn/book/return-book';
import { ReturnBook$Params } from '../fn/book/return-book';
import { saveBook } from '../fn/book/save-book';
import { SaveBook$Params } from '../fn/book/save-book';
import { shareBook } from '../fn/book/share-book';
import { ShareBook$Params } from '../fn/book/share-book';
import { uploadBookCoverPicture } from '../fn/book/upload-book-cover-picture';
import { UploadBookCoverPicture$Params } from '../fn/book/upload-book-cover-picture';
import {GetMyBookList$Params, myBookList} from "../fn/book/get-my-book-waiting-list";
import {addToWaitingList, AddToWaitingList$param} from "../fn/book/add-to-waiting-list";
import {removeBook, RemoveBook$params} from "../fn/book/remove-book-from-waiting-list";




@Injectable({ providedIn: 'root' })
export class BookService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getBooks()` */
  static readonly GetBooksPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBooks()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBooks$Response(params?: GetBooks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponse>> {
    return getBooks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBooks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBooks(params?: GetBooks$Params, context?: HttpContext): Observable<PageResponseBookResponse> {
    return this.getBooks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponse>): PageResponseBookResponse => r.body)
    );
  }

  myBookList$Response(params?:GetMyBookList$Params,context?:HttpContext):Observable<StrictHttpResponse<PageResponseBookResponse>>{
    return myBookList(this.http,this.rootUrl,params,context);
  }

  myBookList(params?:GetMyBookList$Params,context?:HttpContext):Observable<PageResponseBookResponse>{
    return this.myBookList$Response(params,context).pipe(
      map((r:StrictHttpResponse<PageResponseBookResponse>):PageResponseBookResponse=>r.body))
  }

  addToWaitingList$Response(params:AddToWaitingList$param,context?:HttpContext): Observable<StrictHttpResponse<number>>{
    return addToWaitingList(this.http,this.rootUrl,params,context)
  }

  addToWaitingList(params:AddToWaitingList$param,context?:HttpContext): Observable<number>{
    return this.addToWaitingList$Response(params,context).pipe(map((r:StrictHttpResponse<number>):number=>r.body))
  }

  removeBookFromWaitingList$Response(params:RemoveBook$params,context?:HttpContext):Observable<StrictHttpResponse<number>>{
    return removeBook(this.http,this.rootUrl,params,context)
  }
  removeBookFromWaitingList(params:RemoveBook$params,context?:HttpContext):Observable<number>{
    return this.removeBookFromWaitingList$Response(params,context).pipe(map((r:StrictHttpResponse<number>):number=>r.body))
  }

  /** Path part for operation `saveBook()` */
  static readonly SaveBookPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveBook()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveBook$Response(params: SaveBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return saveBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveBook$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveBook(params: SaveBook$Params, context?: HttpContext): Observable<number> {
    return this.saveBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `uploadBookCoverPicture()` */
  static readonly UploadBookCoverPicturePath = '/books/cover/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadBookCoverPicture()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverPicture$Response(params: UploadBookCoverPicture$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return uploadBookCoverPicture(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadBookCoverPicture$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverPicture(params: UploadBookCoverPicture$Params, context?: HttpContext): Observable<{
}> {
    return this.uploadBookCoverPicture$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `borrowBook()` */
  static readonly BorrowBookPath = '/books/borrow/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `borrowBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook$Response(params: BorrowBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return borrowBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `borrowBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook(params: BorrowBook$Params, context?: HttpContext): Observable<number> {
    return this.borrowBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `shareBook()` */
  static readonly ShareBookPath = '/books/shareable/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `shareBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  shareBook$Response(params: ShareBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return shareBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `shareBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  shareBook(params: ShareBook$Params, context?: HttpContext): Observable<number> {
    return this.shareBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `returnBook()` */
  static readonly ReturnBookPath = '/books/borrow/return/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBook$Response(params: ReturnBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return returnBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBook(params: ReturnBook$Params, context?: HttpContext): Observable<number> {
    return this.returnBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `returnApprovedBook()` */
  static readonly ReturnApprovedBookPath = '/books/borrow/return/approved/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnApprovedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnApprovedBook$Response(params: ReturnApprovedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return returnApprovedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnApprovedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnApprovedBook(params: ReturnApprovedBook$Params, context?: HttpContext): Observable<number> {
    return this.returnApprovedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `archivedBook()` */
  static readonly ArchivedBookPath = '/books/archived/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `archivedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  archivedBook$Response(params: ArchivedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return archivedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `archivedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  archivedBook(params: ArchivedBook$Params, context?: HttpContext): Observable<number> {
    return this.archivedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getBookById()` */
  static readonly GetBookByIdPath = '/books/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBookById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookById$Response(params: GetBookById$Params, context?: HttpContext): Observable<StrictHttpResponse<BookResponse>> {
    return getBookById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBookById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookById(params: GetBookById$Params, context?: HttpContext): Observable<BookResponse> {
    return this.getBookById$Response(params, context).pipe(
      map((r: StrictHttpResponse<BookResponse>): BookResponse => r.body)
    );
  }

  /** Path part for operation `findReturnedBook()` */
  static readonly FindReturnedBookPath = '/books/returned';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findReturnedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  findReturnedBook$Response(params?: FindReturnedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>> {
    return findReturnedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findReturnedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findReturnedBook(params?: FindReturnedBook$Params, context?: HttpContext): Observable<PageResponseBorrowedTransactionHistoryResponse> {
    return this.findReturnedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>): PageResponseBorrowedTransactionHistoryResponse => r.body)
    );
  }

  /** Path part for operation `getBookByOwner()` */
  static readonly GetBookByOwnerPath = '/books/owner';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBookByOwner()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookByOwner$Response(params?: GetBookByOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponse>> {
    return getBookByOwner(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBookByOwner$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookByOwner(params?: GetBookByOwner$Params, context?: HttpContext): Observable<PageResponseBookResponse> {
    return this.getBookByOwner$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponse>): PageResponseBookResponse => r.body)
    );
  }

  /** Path part for operation `getBorrowedBook()` */
  static readonly GetBorrowedBookPath = '/books/borrowed';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBorrowedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBorrowedBook$Response(params?: GetBorrowedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>> {
    return getBorrowedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBorrowedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBorrowedBook(params?: GetBorrowedBook$Params, context?: HttpContext): Observable<PageResponseBorrowedTransactionHistoryResponse> {
    return this.getBorrowedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>): PageResponseBorrowedTransactionHistoryResponse => r.body)
    );
  }

}
