import {BookRequest} from "../../models/book-request";
import {HttpClient, HttpContext, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {StrictHttpResponse} from "../../strict-http-response";
import {RequestBuilder} from "../../request-builder";
import {query} from "@angular/animations";
import {filter, map} from "rxjs/operators";

export interface RemoveBook$params{
  'book-id':number
}
export function removeBook(http:HttpClient,rootUrl:string,params?:RemoveBook$params,context?:HttpContext):Observable<StrictHttpResponse<number>>{
  const rb = new RequestBuilder(rootUrl,removeBook.Path,'delete')
  if (params){
    rb.path('book-id',params['book-id'],{})
  }
  return http.request(rb.build({responseType:'json',accept:'application/json',context}))
    .pipe(filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: parseFloat(String((r as HttpResponse<any>).body)) }) as StrictHttpResponse<number>;
      }))
}
removeBook.Path='/books/waiting-list/{book-id}'
