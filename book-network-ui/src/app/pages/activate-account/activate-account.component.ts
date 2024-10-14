import { Component } from '@angular/core';
import {Route, Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent {
  message =''
  isOkay = true
  isSubmitted = false


  constructor(private router:Router, private authService:AuthenticationService) {
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token)

  }

  redirectToLogin() {
    this.router.navigate(['login'])
  }

  private confirmAccount(token: string) {
    this.authService.confirm({token}).subscribe({
      next : (res)=>{
        this.message = 'your account has been successfully activated'
        this.isSubmitted = true
      },
      error :(res)=>{
        this.message ='Token has been expired or invalid please try again'
        this.isSubmitted = true
        this.isOkay=false
      }
    })
  }

  resentTheCode() {

  }
}
