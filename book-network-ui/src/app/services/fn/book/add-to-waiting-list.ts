import {BookRequest} from "../../models/book-request";
import {HttpClient, HttpContext, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {StrictHttpResponse} from "../../strict-http-response";
import {RequestBuilder} from "../../request-builder";
import {filter, map} from "rxjs/operators";

export interface AddToWaitingList$param{
  'book-id':number
}

export function addToWaitingList(http:HttpClient,rootUrl:string,param:AddToWaitingList$param,context?:HttpContext): Observable<StrictHttpResponse<number>>{
  const rb = new RequestBuilder(rootUrl,addToWaitingList.PATH,'post')
  if (param){
    rb.path('book-id',param['book-id'],{})
  }
  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return (r as HttpResponse<any>).clone({ body: parseFloat(String((r as HttpResponse<any>).body)) }) as StrictHttpResponse<number>;
    })
  );
}
addToWaitingList.PATH = '/books/waiting-list/{book-id}'
