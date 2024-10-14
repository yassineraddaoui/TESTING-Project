import { Injectable } from "@angular/core";
import { TokenService } from "./auth/token.service";
import { HttpEvent, HttpHandler, HttpHeaders, HttpRequest } from "@angular/common/http";
import { catchError, from, Observable, switchMap } from "rxjs";

@Injectable()
export class AuthInterceptor{
  constructor(private tokenService:TokenService){}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const promise =  this.tokenService.getToken()

    return from(promise).pipe(
        switchMap((token : string | null) =>{
            if(token){
                const authRequest = request.clone({
                    headers: new HttpHeaders({
                        Authorization: 'Bearer '+token
                    })
                })
                return next.handle(authRequest)
            }
            return next.handle(request)
        }),
        catchError((error)=>{
            console.log(error)
            return next.handle(request)
        })
    )
   

 }
}