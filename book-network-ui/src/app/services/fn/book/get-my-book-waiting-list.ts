import {HttpClient, HttpContext, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {StrictHttpResponse} from "../../strict-http-response";
import {PageResponseBookResponse} from "../../models/page-response-book-response";
import {RequestBuilder} from "../../request-builder";
import {filter, map} from "rxjs/operators";

export interface GetMyBookList$Params {
  page?: number;
  size?: number;
}

export function myBookList(http:HttpClient,rootUrl:string,params?:GetMyBookList$Params, context?:HttpContext) :Observable<StrictHttpResponse<PageResponseBookResponse>>{
  const rb = new RequestBuilder(rootUrl,myBookList.PATH,'get')
  if (params){
    rb.query('page',params.page,{})
    rb.query('size',params.size,{})
  }
  return http.request(rb.build({responseType:'json',accept:'application/json',context}))
    .pipe(
      filter((r:any):r is HttpResponse<any> =>  r instanceof HttpResponse),
      map((r:HttpResponse<any>)=>{
        return r as StrictHttpResponse<PageResponseBookResponse>
      }))
}

myBookList.PATH = '/books/waiting-list'
