import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { AuthenticationRequest } from '../../models/authModels/AuthenticationRequest';
import { TokenService } from '../../services/auth/token.service';
import { AlertController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  authRequest:AuthenticationRequest={
    email: '',
    password: ''
  }
passwordVisibale: boolean=false;


  constructor(private authService:AuthService,private tokenService:TokenService,private alertController:AlertController,private router:Router) {}

  login(){
    this.authService.login(this.authRequest).subscribe({
      next :async (tokenResponse) =>{
        await this.tokenService.setToken(tokenResponse.token)
        this.router.navigate(['books'])
      },
      error: async err =>{
        if(err.error.businessErrorCode == 304){
         const alert = await this.alertController.create({
            header: 'Error',
            message: 'Email or password is incorrect.',
            buttons: ['OK'],
          })
          alert.present()
        }else if (err.error.businessErrorCode == 303){
          const alert = await this.alertController.create({
            header: 'Error',
            message: 'Your account has been disabled. Please check your email for a validation code to reactivate your account. If you do not receive the email within a few minutes, please check your spam folder or contact support for assistance',
            buttons: ['OK'],
          })
          alert.present()

        }
        else if(err.error.businessErrorCode == 303){
          const alert = await this.alertController.create({
            header: 'Error',
            message: 'something went wrong',
            buttons: ['OK'],
          })
          alert.present()

        }
      }
    })
  }

  togglePasswordVisibility() {
   this.passwordVisibale = !this.passwordVisibale
    }

}
