import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { UpdateEmployeeComponent } from '../update-employee/update-employee.component';
import { Employee } from 'src/app/Model/Employee';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ConfirmationDialogData } from 'src/app/Model/ConfirmationDialogData';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { ProjectService } from 'src/app/Services/Project/project.service';
import { Project } from 'src/app/Model/Project';

@Component({
  selector: 'app-view-employes',
  templateUrl: './view-employes.component.html',
  styleUrls: ['./view-employes.component.css']
})
export class ViewEmployesComponent implements OnInit {

  ELEMENT_DATA: Employee[] = [];
  displayedColumns: string[] = ['employeeID', 'employeeName', 'employeeEmail', 'employeePhoneNumber', 'joiningDate', 'actions'];
  dataSource: MatTableDataSource<Employee>;

  constructor(
    private dialog: MatDialog,
    private authentication: AuthnticationService,
    private msg: SnackbarsService,
    private projectService:ProjectService
  ) {
    this.dataSource = new MatTableDataSource<Employee>(this.ELEMENT_DATA);
  }

  ngOnInit(): void {
    this.loadEmployees();
  }


  loadEmployees() {
    this.authentication.getAllEmployees().subscribe(
      (response) => {
        this.ELEMENT_DATA = response;
        this.dataSource.data = this.ELEMENT_DATA; 
      },
      (error) => {
        if (error.status === 404) {
          this.msg.showError('No Employees Here...');
        } else {
          this.msg.showError('Server Error...');
        }
      }
    );
  }


  deleteEmployee(employee: Employee) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '460px',
      data: {
        title: 'Confirmation',
        message: `Do you really want to delete: ${employee.employeeName} ?`,
        confirmText: 'Delete',
        cancelText: 'Cancel'
      } as ConfirmationDialogData,
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.authentication.deleteEmployee(employee).subscribe(
          (response) => {
            if (response === true) {
              this.msg.showSuccess('Employee Deleted Successfully....');
              this.loadEmployees();
            }
          },
          (error) => {
            if (error.status === 404) {
              this.msg.showError('Employee not found...');
            }else{
              
            }
            this.loadEmployees();
          }
        );
      }
    });
  }


  updateEmployee(employee: Employee) {
    const dialogRef = this.dialog.open(UpdateEmployeeComponent, {
      data: employee
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadEmployees();
    });
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  
}
