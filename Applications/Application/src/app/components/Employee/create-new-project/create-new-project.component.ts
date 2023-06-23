import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Route, Router } from '@angular/router';
import { Location } from '@angular/common';
import { Project } from 'src/app/Model/Project';
import { ProjectService } from 'src/app/Services/Project/project.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-create-new-project',
  templateUrl: './create-new-project.component.html',
  styleUrls: ['./create-new-project.component.css']
})
export class CreateNewProjectComponent implements OnInit {


  projectForm!: FormGroup;
  currentDate: string;

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private route:Router,
    private msg:SnackbarsService,
    private dialog:MatDialog,
   
    
  ) {
    this.currentDate = new Date().toISOString().split('T')[0];
  }

  ngOnInit() {
    this.projectForm = this.formBuilder.group({
      projectName: ['', [Validators.required, Validators.pattern(/^[A-Za-z][A-Za-z0-9\s]*$/)]],
      createdDate: [this.currentDate],
      endDate: ['', [Validators.required,this.endDateValidator]],
      description: ['', Validators.maxLength(200)]
    });
  }

  createProject() {
    if (this.projectForm.valid) {
      const project: Project = {
        projectID: 0,
        projectName: this.projectForm.get('projectName')?.value,
        projectDescription: this.projectForm.get('description')?.value,
        createdBY: '',
        projectCreatedDate: this.projectForm.get('createdDate')?.value,
        projectEndDate: new Date(this.projectForm.get('endDate')?.value).toISOString().split('T')[0],
        taskList: [],
        employeeList: []
      };
      this.projectService.createProject(project).subscribe(
        (response) => {
          if(response===true){
            this.dialog.closeAll();
            this.msg.showSuccess("Project Created Successfully....")
            this.projectForm.reset();
          }
        },
        (error) => {
          if(error.status === 404){
            this.msg.showError("Project Not Created.....");
          }
          else{
            this.msg.showError("Server Error....");
          }
        }
      );
    } else {
      
    }
  }




  endDateValidator(control: any) {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();
  
    // Set the time of the current date to 00:00:00 to compare only the dates
    currentDate.setHours(0, 0, 0, 0);
  
    // Compare the selected date with the current date
    if (selectedDate < currentDate && selectedDate.toDateString() !== currentDate.toDateString()) {
      return { endDateInvalid: true };
    }
  
    return null;
  }
  

  cancel() {
    this.dialog.closeAll();
  }
}

