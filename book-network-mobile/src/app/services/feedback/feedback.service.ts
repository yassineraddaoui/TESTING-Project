import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseRoute } from 'src/app/Helper/BaseRoute';
import { FeedbackRequest } from 'src/app/models/feedback/FeedBackRequest';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private readonly saveFeedbackUrl:string="/feedbacks"

  constructor(private http:HttpClient) { }

  saveFeedback(feedback:FeedbackRequest){
    return this.http.post(`${BaseRoute.rootUrl}${this.saveFeedbackUrl}`,feedback)
  }
}
