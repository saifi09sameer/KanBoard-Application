import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ITask } from 'src/app/Model/task';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  constructor(private http:HttpClient) { }

  createNewTask(taskData: ITask, projectCreatedEmployee: string, projectID: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('Token')
      })
    };
    const url = `http://localhost:7860/api/Task/createNewTask/${projectCreatedEmployee}/${projectID}`;
    return this.http.put<boolean>(url, taskData, httpOptions);
  }

  deleteTask(projectCreatedEmployee:string,projectID:number,taskID:number){
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('Token')
      })
    };
    const url = `http://localhost:7860/api/Task/deleteTask/${projectCreatedEmployee}/${projectID}/${taskID}`;
    return this.http.delete<boolean>(url,httpOptions);
  }

  updateTask(updatedTaskDescription:string,projectCreatedBY:string,projectID:number,taskID:number){
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('Token')
      })
    };
    const url = `http://localhost:7860/api/Task/updateTask/${projectCreatedBY}/${projectID}/${taskID}/${updatedTaskDescription}`;
    return this.http.put<boolean>(url,null,httpOptions)

  }
  
  changeStageOFTask(taskID: number, taskUpdatedStage: string, projectCreatedEmployee: string, projectID: number) {
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
  
    const url = `http://localhost:7860/api/Task/updateTaskStage/${projectCreatedEmployee}/${projectID}/${taskID}/${taskUpdatedStage}`;
  
    const requestOptions = { headers: httpHeader };
  
    return this.http.put<boolean>(url, null, requestOptions);
  }

  assignTask(projectCreatedBy:string,projectID:number,taskID:number,assignEmployee:string){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };

    const url = `http://localhost:7860/api/Task/assignTask/${projectCreatedBy}/${projectID}/${taskID}/${assignEmployee}`;
    return this.http.put<boolean>(url, null, requestOptions);
  }



  getAssignEmployeeList(projectCreatedBy:string,projectID:number,taskID:number){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/getAssignEmployeeList/${projectCreatedBy}/${projectID}/${taskID}`;
    return this.http.get<string[]>(url,requestOptions);
  }

  removeAssignEmployee(projectCreatedBy:string,projectID:number,taskID:number,removeEmployee:string){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/removeAssignEmployee/${projectCreatedBy}/${projectID}/${taskID}/${removeEmployee}`;
    return this.http.delete<boolean>(url,requestOptions);
  }


  getAllFilterEmployess(employeeEmailList:string[],projectCreatedBY:string,projectID:number){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/getEmployeesList/${projectCreatedBY}/${projectID}`;
    return this.http.post<string[]>(url,employeeEmailList,requestOptions);
  }

  getAllDeletedTasks(projectCreatedBY:string,projectID:number){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/getAllDeletedTasks/${projectCreatedBY}/${projectID}`;
    return this.http.get<ITask[]>(url,requestOptions);
  }

  deleteAllDeletedTasks(projectCreatedBY:string,projectID:number){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/deleteAllDeletedTasks/${projectCreatedBY}/${projectID}`;
    return this.http.delete<boolean>(url,requestOptions);
  }

  filteredEmployeeListForAssign(employeeList:string[],projectCreatedBY:string,projectID:number,taskID:number){
    const httpHeader = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem("Token")
    });
    const requestOptions = { headers: httpHeader };
    const url = `http://localhost:7860/api/Task/filteredEmployeeListForAssign/${projectCreatedBY}/${projectID}/${taskID}`;
    return this.http.put<string[]>(url,employeeList,requestOptions);
  }

}
