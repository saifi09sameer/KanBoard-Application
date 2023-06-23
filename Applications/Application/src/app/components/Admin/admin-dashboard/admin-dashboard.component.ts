import { Component, OnInit } from '@angular/core';
import { Employee } from 'src/app/Model/Employee';
import { Project } from 'src/app/Model/Project';
import { AdminService } from 'src/app/Services/Authntication/admin.service';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { OnlineStatusService } from 'src/app/Services/Authntication/online-status.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  totalProjects:any;
  totalTasks:any;
  top5Projects:Project[]=[];
  employees: Employee[]=[];
  onlineEmployees:string[]=[]
  constructor(private authentication: AuthnticationService, private onlineService: OnlineStatusService,
    private admin:AdminService){}

  ngOnInit(): void {
    this.countNumberOFProjectAndTask();
    this.loadEmployees();
    this.getAllEmployeeStatus();
    this.getTop5Projects();
  }

  loadEmployees() {
    this.authentication.getAllEmployees().subscribe(
      (response) => {
        this.employees = response;
      },
      (error) => {
        
      }
    );
  }
  getAllEmployeeStatus() {
    this.onlineService.getAllEmployeesStatus().subscribe(
      (response) => {
        this.onlineEmployees = response;
      },
      (error) => {
        console.log(error + "EmployeeNot Found");
      }
    )
  }
  responseData:any;
  countNumberOFProjectAndTask(){
    this.admin.countNumberOFProjectAndTask().subscribe(
      (response)=>{
       this.responseData=response;
       this.totalProjects=this.responseData.projects;
       this.totalTasks=this.responseData.tasks;
      },
      (error)=>{

      }
    )
  }

  getTop5Projects(){
    this.admin.getTop5Projects().subscribe(
      (response)=>{
        this.top5Projects=response;
      },
      (error)=>{

      }
    )
  }
}
