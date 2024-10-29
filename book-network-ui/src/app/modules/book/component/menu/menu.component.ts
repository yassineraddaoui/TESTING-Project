  import {Component, OnInit} from '@angular/core';
  import {TokenService} from "../../../../services/token/token.service";
  import {jwtDecode} from "jwt-decode";
  import * as SockJS from 'sockjs-client';
  import * as Stomp from 'stompjs';
  import {Notification} from './Notification'
  import {ToastrService} from "ngx-toastr";
  import {CustomJwtPayload} from "./customJwtPayload";



  @Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
  })
  export class MenuComponent implements OnInit{
    socketClient:any = null
    notificationSubscription:any
    unreadNotificationCount = 0
    notifications : Array<Notification> = []
    constructor(private tokenService:TokenService,private toastService:ToastrService) {
    }
    ngOnInit(): void {
      // this.navigationHandler()
      if (this.getAuthenticatedUser()){
        let ws = new SockJS('http://localhost:8080/api/v1/ws')
        this.socketClient = Stomp.over(ws)
        this.socketClient.connect({'Authorization:': 'Bearer ' + this.tokenService.token},()=>{

          this.notificationSubscription = this.socketClient.subscribe(
            `/user/${this.getAuthenticatedUser()}/notification` ,
            (message: any)=>{
              const notification : Notification= JSON.parse(message.body)
              if (notification){
                this.notifications.unshift(notification)
                switch (notification.status){
                  case ("BORROWED"):
                    this.toastService.info(notification.message,notification.bookTitle)
                    break
                  case ("RETURNED"):
                      this.toastService.warning(notification.message,notification.bookTitle)
                    break
                  case ("RETURN_APPROVED"):
                      this.toastService.success(notification.message,notification.bookTitle)
                    break
                }
                this.unreadNotificationCount++
              }
            })
        })
      }
    }

  getDecodedToken(){
    const token = this.tokenService.token
    if (token){
      return jwtDecode<CustomJwtPayload>(token)
    }
    return null
  }
  getAuthenticatedUser(){
    const decodedToken = this.getDecodedToken()
    if (decodedToken){
      return decodedToken['sub']
    }
    return null
  }

  private navigationHandler(){
  const  linkColor = document.querySelectorAll('.nav-link');
  linkColor.forEach(link => {
    if (window.location.href.endsWith(link.getAttribute('href') || '')){
      link.classList.add('active')
    }
    link.addEventListener('click',()=>{
      linkColor.forEach(l=>l.classList.remove('active'))
      link.classList.add('active')
    })
  })

  }




  logout() {
    localStorage.removeItem('token')
    window.location.reload()
  }
  get fullName(){
      const decoded= this.getDecodedToken()
    if (decoded){
      return decoded['fullName']
    }
    return null
  }


}
