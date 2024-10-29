import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {App, URLOpenListener, URLOpenListenerEvent} from '@capacitor/app'
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  constructor(private router:Router, private menuController:MenuController) {}
  

  initialApp(){
    App.addListener('appUrlOpen',(data:URLOpenListenerEvent)=>{
      if(data.url && data.url.includes('bookApp://confirm')){
        this.router.navigate(['activation-account'])
      }

    })
  }

  closeMenu() {
    this.menuController.close('first'); 
  }
}
