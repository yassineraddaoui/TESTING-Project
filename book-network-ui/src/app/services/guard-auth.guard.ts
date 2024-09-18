import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {TokenService} from "./token/token.service";

export const guardAuthGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService)
  const routeService = inject(Router)
  if (tokenService.isExpired()){
    routeService.navigate(['login'])
    return false
  }
  return true;
};
