import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest } from 'src/app/models/authModels/AuthenticationRequest';
import { BaseRoute } from '../../Helper/BaseRoute';
import { AuthenticationResponse } from 'src/app/models/authModels/AuthenticationResponse';
import { RegistrationRequest } from 'src/app/models/authModels/RegistrationRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly authUrl= "/auth/authenticate"
  private readonly registerUrl= "/auth/register"
  private readonly activationUrl= "/auth/activate-account"


  constructor(private http:HttpClient) { }

  login(authRequest:AuthenticationRequest){
    return this.http.post<AuthenticationResponse>(`${BaseRoute.rootUrl}${this.authUrl}`,authRequest)
  }

  register(registerRequest:RegistrationRequest){
    return this.http.post(`${BaseRoute.rootUrl}${this.registerUrl}`,registerRequest)
  }

  activationAccount(parm:TokenParam){
    return this.http.get(`${BaseRoute.rootUrl}${this.activationUrl}`,{params:{...parm}})
  }
}

export interface TokenParam{
  token:string
}
