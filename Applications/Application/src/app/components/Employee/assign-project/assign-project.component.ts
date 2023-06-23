import { Component, Inject, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Observable, map, startWith } from 'rxjs';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { ProjectService } from 'src/app/Services/Project/project.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
@Component({
  selector: 'app-assign-project',
  templateUrl: './assign-project.component.html',
  styleUrls: ['./assign-project.component.css']
})
export class AssignProjectComponent implements OnInit {
  isVisible: boolean = false;
  projectData: any;

  employeeList: string[] = [];
  assignedEmployees: string[] = [];

  showDropdown: boolean= false;
  searchEmployee: string = '';

  constructor(
    private projectService: ProjectService,
    private authentication: AuthnticationService,
    private matDialog: MatDialogRef<AssignProjectComponent>,
    private snack: SnackbarsService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.projectData = data;
  }

  ngOnInit() {
    this.loadAllEmployeeEmail();
    this.getAssignEmployeeList();
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  selectEmployee(employee: string) {
    this.searchEmployee = employee;
    this.showDropdown = false;
  }

  filterEmployees() {
    if (this.searchEmployee.trim() !== '') {
      this.employeeList = this.employeeList.filter(employee =>
        employee.toLowerCase().includes(this.searchEmployee.toLowerCase())
      );
    } else {
      this.employeeList = this.retriveAllEmployeeList;
    }
  }

  assignEmployeesToProject() {
    if (this.data.projectID && this.searchEmployee) {
      if (!this.employeeList.includes(this.searchEmployee)) {
        this.snack.showError('Invalid employee. Please select an employee from the dropdown.');
        return;
      }
      
      this.showDropdown = true;
      this.projectService.AssignProject(this.searchEmployee, this.data.projectID).subscribe(
        (response) => {
          if (response === true) {
            this.snack.showSuccess('Employee assigned successfully!');
            this.searchEmployee = '';
            this.filteredEmployeeList();
            this.getAssignEmployeeList();
          } else {
            this.snack.showError('Failed to assign employees!');
          }
          this.showDropdown = false;
        },
        (error) => {
          
        }
      );
    }
  }

  retriveAllEmployeeList: string[] = [];

  loadAllEmployeeEmail() {
    this.showDropdown = true;
    this.authentication.getAllEmployeesEmails().subscribe(
      (response) => {
        this.retriveAllEmployeeList = response;
        this.filteredEmployeeList();
      },
      (error) => {
        if (error.status === 404) {
          this.snack.showError('Employees Not Found !!!');
        }
      }
    );
  }

  filteredEmployeeList() {
    this.projectService.getEmployeeLists(this.retriveAllEmployeeList).subscribe(
      (response) => {
        this.employeeList = response;
        this.projectService.filteredEmployeeListForAssign(this.employeeList, this.data.projectID).subscribe(
          (filteredResponse) => {
            this.employeeList = filteredResponse;
          }
        );
        this.showDropdown = false;
      },
      (error) => {

      }
    );
  }

  getAssignEmployeeList() {
    this.projectService.getAssignEmployeeList(this.data.projectID).subscribe(
      (response) => {
        this.assignedEmployees = response;
      },
      (error) => {
        if (error.status === 404) {
          this.snack.showError('Assigned employees not found!');
        }
      }
    );
  }

  removeEmployee(employeeEmail: string) {
    this.isVisible = true;
    this.projectService.deleteAssignEmployee(this.data.projectID, employeeEmail).subscribe(
      (response) => {
        if (response === true) {
          this.isVisible = false;
          this.snack.showSuccess('Employee removed successfully!');
          this.filteredEmployeeList();
          this.getAssignEmployeeList();
        }
      },
      (error) => {
        if (error.status === 404) {
          this.snack.showError('Failed to remove employee!');
        }
      }
    );
  }

  closeDialog(): void {
    this.matDialog.close();
 }

}
