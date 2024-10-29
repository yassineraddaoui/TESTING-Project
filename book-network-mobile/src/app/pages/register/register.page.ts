import { Component, OnInit } from '@angular/core';
import { RegistrationRequest } from '../../models/authModels/RegistrationRequest';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { passowrdValidator } from '../../Helper/customValidators';
import { AuthService } from '../../services/auth/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  registrationRequest:RegistrationRequest={}
  registerGroup!:FormGroup
  formSubmitted:boolean=false
  uniqueEmailValidation=false
  passwordVisibale:boolean = false

  constructor(private authService:AuthService,private router:Router) {
    this.registerGroup = new FormGroup({
      firstname: new FormControl("",Validators.required),
      lastname: new FormControl("",Validators.required),
      email: new FormControl("",[Validators.required,Validators.email]),
      password: new FormControl("",[Validators.required,passowrdValidator])
    })
  }

  ngOnInit() {
  }

  togglePasswordVisibility() {
    this.passwordVisibale = !this.passwordVisibale
    }

register(){
  this.registrationRequest = this.registerGroup.value
  this.authService.register(this.registrationRequest).subscribe({
    next : ()=>{
      this.router.navigate(['activation-account'])
    },
    error: (err)=>{
      this.formSubmitted= true
      if(err.error.businessErrorCode == 305){
        this.uniqueEmailValidation = true
      }else{
        this.uniqueEmailValidation = false
      }

    }
  })

}

}
