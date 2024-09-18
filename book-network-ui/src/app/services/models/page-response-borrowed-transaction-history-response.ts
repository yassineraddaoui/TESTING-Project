/* tslint:disable */
/* eslint-disable */
import { BorrowedTransactionHistoryResponse } from '../models/borrowed-transaction-history-response';
export interface PageResponseBorrowedTransactionHistoryResponse {
  content?: Array<BorrowedTransactionHistoryResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElement?: number;
  totalPages?: number;
}
