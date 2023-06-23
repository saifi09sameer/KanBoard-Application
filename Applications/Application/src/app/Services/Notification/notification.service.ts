import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http:HttpClient) { }

   //http://localhost:9933/api/Notification/getAllNotifications/ {Path Variables} [TOKEN]

   getAllNotifications(){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    return this.http.get<string[]>("http://localhost:7860/api/Notification/getAllNotifications/",requestOptions);
   }

 
}
