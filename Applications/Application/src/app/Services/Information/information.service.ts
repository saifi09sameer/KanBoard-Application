import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InformationService {

  constructor() { }

  isLogin:boolean=false;
  roleActive:string="";

  setToLogin(){
    this.isLogin=true;
  }
  
  removeToLogin(){
    this.isLogin=false;
  }

  setRole(employeeRole:string){
    this.roleActive=employeeRole;
  }



  isloggedin(){
    return localStorage.getItem('employeeEmail')!=null;
  }

  getrole(){
    return localStorage.getItem('employeeRole')!=null?localStorage.getItem('employeeRole')?.toString():'';
  }

  removeRole(){
    this.roleActive="";
  }



  getRole(){
    return this.roleActive;
  }
}
