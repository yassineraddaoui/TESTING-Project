import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {register} from "../../services/fn/authentication/register";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {email:'',password:''}
  errorMsg: Array<string> = []
  constructor(private router:Router,
              private authService: AuthenticationService,
              private tokenService:TokenService) {
  }

  login() {
    this.errorMsg = []
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next : (res)=>{
        this.tokenService.token = res.token as string
        this.router.navigate(['books'])
      },
      error:(err)=>{
        console.log(err)
        if (err.error.businessErrorCode == 304){
          this.errorMsg.push(err.error.businessErrorCode)
        }else {
          this.errorMsg.push(err.error.error)
          if (err.error.businessErrorCode == 303){
            console.log(err.error.businessErrorCode)
            this.authService.resentCodeConfirmation(this.authRequest.email).subscribe({
              next : (res)=>{this.router.navigate(['activate-account'])}
            })
          }
        }
      }
    })

  }
register(){
    this.router.navigate(['register'])
}
}
