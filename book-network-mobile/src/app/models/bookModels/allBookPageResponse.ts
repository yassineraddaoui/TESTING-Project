import { BookResponse } from "./BookResponse"


export interface BookPageResponse{
    content?:Array<BookResponse>
    number? : number
    size?: number
    totalElement?: number
    totalPages?: number
    first?: number
    last?: number
}

export interface PageParams{
    page?:number
    size?:number
}