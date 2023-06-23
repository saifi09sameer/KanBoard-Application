import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contact } from 'src/app/Model/Contact';
import { Project } from 'src/app/Model/Project';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }
  countNumberOFProjectAndTask(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
    let requestOption={headers:httpHeader}
    return this.http.get("http://localhost:7860/api/Admin/countNumberOFProjectAndTask",requestOption);
  }

  getTop5Projects(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
    let requestOption={headers:httpHeader}
    return this.http.get<Project[]>("http://localhost:7860/api/Admin/getTop5Projects",requestOption);
  }

  getAllMessages(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.get<Contact[]>("http://localhost:7860/api/Contact/getAllMessages",requestOption);
  }

  sendMsgToAdmin(contact:Contact){
    return this.http.post("http://localhost:7860/api/Contact/sendMessage",contact);
  }


  deleteMessages(contactID: number) {
    let httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('Token')
    });
    let requestOptions = { headers: httpHeader };
    return this.http.delete<boolean>('http://localhost:7860/api/Contact/deleteMessages/' + contactID, requestOptions);
  }
  

}
