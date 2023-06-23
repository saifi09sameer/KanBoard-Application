import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee, EmployeeLogin, EmployeeRegister } from 'src/app/Model/Employee';

@Injectable({
  providedIn: 'root'
})
export class AuthnticationService {

  private apiUrl = 'http://localhost:9911/api/Authentication';

  constructor(private http: HttpClient) {}

  login(employee: EmployeeLogin){
    console.log(employee)
    return this.http.post("http://localhost:7860/api/Authentication/loginEmployee",employee);
  }

  register(employee: EmployeeRegister){
    return this.http.post("http://localhost:7860/api/Project/registerEmployee",employee);
  }

  getEmployeeDetails(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
    return this.http.get("http://localhost:7860/api/Authentication/getEmployeeDetails",requestOption);
  }

 sendOTP(employeeEmail: string) {
    return this.http.get<number>(`http://localhost:7860/api/Authentication/sendOTP/${employeeEmail}`);
  }

  forgotPassword(employeeEmail: string, employeePassword: string) {
    const url = `http://localhost:7860/api/Authentication/forgotPassword/${employeeEmail}/${employeePassword}`;
    return this.http.put(url, null, { responseType: 'text' });
  }
  

  updateEmployee(updatedEmploye:Employee){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.put("http://localhost:7860/api/Authentication/updateEmployee/",updatedEmploye,requestOption);
  }

  deleteEmployee(employee: Employee): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('Token')
      })
    };

    const url = `http://localhost:7860/api/Authentication/deleteEmployee/${employee.employeeEmail}`;
    return this.http.delete<boolean>(url, httpOptions);
  }
  getAllEmployees(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
    return this.http.get<Employee[]>("http://localhost:7860/api/Authentication/getAllEmployee",requestOption);
  }

  getAllEmployeesEmails(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
    return this.http.get<string[]>("http://localhost:7860/api/Authentication/getEmployeeEmailList",requestOption)
  }

}
