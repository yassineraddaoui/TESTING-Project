import { BookResponse } from "./BookResponse"

export interface MyBookPageResponse{
   
    content?:Array<BookResponse>
    number? : number
    size?: number
    totalElement?: number
    totalPages?: number
    first?: number
    last?: number
}