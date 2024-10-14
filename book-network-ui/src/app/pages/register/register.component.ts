import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {register} from "../../services/fn/authentication/register";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerRequest: RegistrationRequest ={email:'',firstname:'',lastname:'',password:''}
  errorMsg: Array<string>=[]

 constructor(private router:Router,
             private registerService:AuthenticationService) {
 }

  login() {
    this.router.navigate(['login'])

  }
  register(){
    this.registerService.register({
      body :this.registerRequest
    }).subscribe({
      next : (res => {
       this.router.navigate(['activate-account'])
      }),
      error : (err)=>{
        if (err.error.validationErrors){
          this.errorMsg = err.error.validationErrors
        }else {
          this.errorMsg.push(err.error.error)
        }
      }
    })

  }
}
