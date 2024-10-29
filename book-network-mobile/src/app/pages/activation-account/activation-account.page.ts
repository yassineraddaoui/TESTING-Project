import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-activation-account',
  templateUrl: './activation-account.page.html',
  styleUrls: ['./activation-account.page.scss'],
})
export class ActivationAccountPage implements OnInit {
  token:string=''

  constructor(private authService:AuthService,private router:Router) { }

  ngOnInit() {
  }

  activationAccount(){
    this.authService.activationAccount({token:this.token}).subscribe({
      next : () => {
        this.router.navigate(['home'])
      },
      error: err =>{
      }
    })
  
  }
  }


