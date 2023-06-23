import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OnlineStatusService {

  constructor(private http:HttpClient) { }
  baseURL:string="http://localhost:7860/api/Authentication/getAllEmployeesStatus";

  getAllEmployeesStatus(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.get<string[]>(this.baseURL,requestOption);
  }

  setEmployeeStatusOffline(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.get("http://localhost:7860/api/Authentication/offLineStatus",requestOption);
  }
}
