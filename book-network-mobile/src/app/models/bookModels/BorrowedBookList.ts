import { BookResponse } from "./BookResponse"
import { BorrowedTransactionHistoryResponse } from "../authModels/BorrowedTransactionHistoryResponse"

export interface MyBoorowedBookPageResponse{

    content?:Array<BorrowedTransactionHistoryResponse>
    number? : number
    size?: number
    totalElement?: number
    totalPages?: number
    first?: number
    last?: number
}
