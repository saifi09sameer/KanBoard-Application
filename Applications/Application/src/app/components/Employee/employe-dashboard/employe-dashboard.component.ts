import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Project } from 'src/app/Model/Project';
import { ProjectService } from 'src/app/Services/Project/project.service';
import { AssignProjectComponent } from '../assign-project/assign-project.component';
import { CreateNewProjectComponent } from '../create-new-project/create-new-project.component';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { ConfirmationDialogData } from 'src/app/Model/ConfirmationDialogData';

@Component({
  selector: 'app-employe-dashboard',
  templateUrl: './employe-dashboard.component.html',
  styleUrls: ['./employe-dashboard.component.css']
})
export class EmployeDashboardComponent implements OnInit {
  constructor(private projectService: ProjectService,
    private dilog: MatDialog,
    private msg: SnackbarsService,
    private dialog: MatDialog
  ) { }

  LoginemployeeEmail: any;
  projectList: Project[] = []


  ngOnInit(): void {
    this.LoginemployeeEmail = localStorage.getItem("employeeEmail");
    this.loadAllProject();
  }

  loadAllProject() {
    this.projectService.getAllProject().subscribe(
      (response) => {
        this.projectList = response;
      },
      (error) => {
        if (error.status === 404) {
          this.msg.showError("You Don't Have Any Projects...");
        }
        else {
          this.msg.showError("Server Error...");
        }
      }
    )
  }

  deleteProject(projectID: number) {

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmation',
        message: 'Do you really want to delete this project?',
        confirmText: 'Delete',
        cancelText: 'Cancel'
      } as ConfirmationDialogData,
    });
    dialogRef.afterClosed().subscribe(response => {
      if (response == true) {
        this.projectService.deleteProject(projectID).subscribe(
          (response) => {
            if (response == true) {
              this.loadAllProject();
              this.msg.showSuccess("Project Delete Successfully....");
            }
          },
          (error) => {
            if (error.status === 404) {
              this.msg.showError("Project Not Delete...");
            }
            else {
              this.msg.showError("Server Error....");
            }
          }
        )
      }
    });
  }

  openAssign(projectID: number) {
    const dialogRef = this.dilog.open(AssignProjectComponent, {
      data: { projectID: projectID }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }

  openCreateProject() {
    const dialogRef = this.dilog.open(CreateNewProjectComponent, {

    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadAllProject();
    });
  }

}
