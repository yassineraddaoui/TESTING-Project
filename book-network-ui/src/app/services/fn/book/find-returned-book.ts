/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseBorrowedTransactionHistoryResponse } from '../../models/page-response-borrowed-transaction-history-response';

export interface FindReturnedBook$Params {
  page?: number;
  size?: number;
}

export function findReturnedBook(http: HttpClient, rootUrl: string, params?: FindReturnedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>> {
  const rb = new RequestBuilder(rootUrl, findReturnedBook.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseBorrowedTransactionHistoryResponse>;
    })
  );
}

findReturnedBook.PATH = '/books/returned';
