import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Project } from 'src/app/Model/Project';
import { ProjectService } from 'src/app/Services/Project/project.service';

@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.component.html',
  styleUrls: ['./view-project.component.css']
})
export class ViewProjectComponent implements OnInit {

  projectData!:Project;
  projectID:any;
  loginEmployee:any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { projectId: string } ,private dialogRef: MatDialogRef<ViewProjectComponent>,
    private projectService:ProjectService) {
      this.projectID=data.projectId;
    }


  ngOnInit(): void {
    this.loginEmployee=localStorage.getItem("employeeEmail");
    this.loadProjectDetails();
  }

  loadProjectDetails(){
    this.projectService.getProjectBasedOnID(this.projectID).subscribe(
      (response)=>{
        this.projectData=response;
      },
      (error)=>{

      }
    )
  }
  

  closeDialog(): void {
    this.dialogRef.close();
  }

}
