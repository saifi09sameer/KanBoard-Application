import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from 'src/app/Model/Project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http:HttpClient) { }
  
  getAllProject(){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.get<Project[]>("http://localhost:7860/api/Project/getProjects",requestOption);
  }

  createProject(newProject:Project){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.put<boolean>("http://localhost:7860/api/Project/createProject",newProject,requestOption);
  }
  
  deleteProject(projectID:number){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.delete<boolean>("http://localhost:7860/api/Project/deleteProject/"+projectID,requestOption);
  }

  getEmployeeLists(employeeEmailList:string[]){
    let httpHeader = new HttpHeaders({
      'Authorization':'Bearer '+ localStorage.getItem("Token")
    });
   let requestOption={headers:httpHeader}
   return this.http.post<string[]>("http://localhost:7860/api/Project/getEmployeesList",employeeEmailList,requestOption);
  }

  AssignProject(employeeEmail: string, projectID: number) {
    let httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    let requestOption = { headers: httpHeader };
    return this.http.put<boolean>("http://localhost:7860/api/Project/assignEmployeeToProjects/" + projectID+ "/"+ employeeEmail,null, requestOption);
  }

  getProjectBasedOnID(projectID: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('Token')
      })
    };
    return this.http.get<Project>("http://localhost:7860/api/Project/findProjectBasedOnProjectID/" + projectID, httpOptions);
  }
  
  getAssignEmployeeList(projectID :number){
    let httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    let requestOption = { headers: httpHeader };
    return this.http.get<string[]>("http://localhost:7860/api/Project/getAssignEmployeeList/"+projectID,requestOption);
  }
  deleteAssignEmployee(projectID:number,employeeEmail:string){
    let httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    let requestOption = { headers: httpHeader };
    return this.http.delete<boolean>("http://localhost:7860/api/Project/deleteAssignEmployee/"+projectID+"/"+employeeEmail,requestOption);
  }

  filteredEmployeeListForAssign(employeeEmailList:string[],projectID:number){
    let httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    let requestOption = { headers: httpHeader };
    return this.http.put<string[]>("http://localhost:7860/api/Project/filteredEmployeeListForAssign/"+projectID, employeeEmailList, requestOption);
  }

  getNoOFProjects(employeeEmail: string): Observable<Project[]> {
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('Token')
    });
    const requestOptions = { headers: httpHeader };
    return this.http.get<Project[]>(`http://localhost:7860/api/Project/getNoOFProjects/${employeeEmail}`, requestOptions);
  }
}
