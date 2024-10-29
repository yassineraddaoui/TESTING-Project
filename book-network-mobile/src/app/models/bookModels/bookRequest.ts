export interface BookRequest{
    id?:number
    title?:string
    authorName?:string
    isbn?:string
    synopsis?:string
    shareable?:boolean
    cover?: Array<string>;
}