import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Employee } from 'src/app/Model/Employee';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {
  // progressDisplay:boolean = false;
  // employeeForm!: FormGroup;

  // constructor(
  //   @Inject(MAT_DIALOG_DATA) public employee: Employee,
  //   private formBuilder: FormBuilder,
  //   private dialogRef: MatDialogRef<UpdateEmployeeComponent>,
  //   private authentication:AuthnticationService,
  //   private msg:SnackbarsService
  // ) {}

  // ngOnInit() {
  //   // ...
  
  //   this.employeeForm = this.formBuilder.group({
  //     employeeID: [this.employee.employeeID],
  //     employeeName: [this.employee.employeeName, Validators.required],
  //     gender: [this.employee.gender, Validators.required],
  //     maritalStatus: [this.employee.maritalStatus, Validators.required],
  //     employeeDOB: [this.employee.employeeDOB, Validators.required],
  //     employeeAadhaarNumber: [this.employee.employeeAadhaarNumber, Validators.required],
  //     bloodGroup: [this.employee.bloodGroup, Validators.required],
  //     employeePhoneNumber: [this.employee.employeePhoneNumber, [Validators.required, Validators.pattern(/^[6-9]\d{9}$/)]],
  //     employeeEmail: [this.employee.employeeEmail, [Validators.required, Validators.email,Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z-]+\.[a-zA-Z]{2,4}')]],
  //     joiningDate: [this.employee.joiningDate, Validators.required],
  //     employeeRole: [this.employee.employeeRole, Validators.required],
  //     nationality: [this.employee.nationality, Validators.required],
  //     employeeCity: [this.employee.employeeCity, Validators.required],
  //     imageURL: [this.employee.imageURL],
  //     status: [this.employee.status, Validators.required]
  //   });
  // }

  // updateEmployee() {
  //   this.progressDisplay = true;
  //   const updatedEmployee: Employee = this.employeeForm.value;
  //   console.log(updatedEmployee);
  //   this.authentication.updateEmployee(updatedEmployee).subscribe(
  //     (response) => {
  //       this.msg.showSuccess("Employee Update Successfully")
        
  //       this.progressDisplay = false;
  //       this.onCancel();
  //     },
  //     (error) => {
  //       this.progressDisplay = false;
  //       if (error.status === 404) {
  //         this.msg.showError('this is not a Employee');
  //       } else {
  //         this.msg.showError('Error updating employee. Please try again.');
  //       }
  //       this.onCancel();
  //     }
  //   );
  // }
  

  // onCancel() {
  //   this.dialogRef.close();
  // }

  progressDisplay:boolean = false;
  employeeForm!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public employee: Employee,
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<UpdateEmployeeComponent>,
    private authentication:AuthnticationService,
    private msg:SnackbarsService
  ) {}

  ngOnInit() {
    console.log(this.employee)
    this.employeeForm = this.formBuilder.group({
      employeeID: [this.employee.employeeID],
      employeeName: [this.employee.employeeName, [Validators.required, Validators.pattern(/^[a-zA-Z][a-zA-Z0-9\s]*$/)]],
      gender: [this.employee.gender, Validators.required],
      maritalStatus: [this.employee.maritalStatus, Validators.required],
      employeeDOB: [this.employee.employeeDOB, [Validators.required, this.dateValidator]],
      employeeAadhaarNumber: [this.employee.employeeAadhaarNumber, [Validators.required, Validators.pattern(/^\d{12}$/)]],
      bloodGroup: [this.employee.bloodGroup, Validators.required],
      employeePhoneNumber: [this.employee.employeePhoneNumber,  [Validators.required, Validators.pattern(/^[6-9]\d{9}$/)]],
      employeeEmail: [this.employee.employeeEmail],
      employeePassword: [this.employee.employeePassword],
      joiningDate: [this.employee.joiningDate],
      employeeRole: [this.employee.employeeRole,Validators.required],
      nationality: [this.employee.nationality, [Validators.required, Validators.pattern(/^[a-zA-Z][a-zA-Z0-9\s]*$/)]],
      employeeCity: [this.employee.employeeCity, [Validators.required, Validators.pattern(/^[a-zA-Z][a-zA-Z0-9\s]*$/)]],
      imageURL: [this.employee.imageURL],
      status: [this.employee.status]
    });
  }

  updateEmployee() {
    this.progressDisplay = true;
    const updatedEmployee: Employee = this.employeeForm.value;
    console.log(updatedEmployee);
    this.authentication.updateEmployee(updatedEmployee).subscribe(
      (response) => {
        this.msg.showSuccess("Employee Update Successfully")
        
        this.progressDisplay = false;
        this.onCancel();
      },
      (error) => {
        this.progressDisplay = false;
        if (error.status === 404) {
          this.msg.showError('Employee not found. Please try again.');
        } else {
          this.msg.showError('Error updating employee. Please try again.');
        }
        this.onCancel();
      }
    );
  }
  
  dateValidator(control: any) {
    const inputDate = new Date(control.value);
    const currentDate = new Date();
    if (inputDate >= currentDate) {
      return { futureDate: true };
    }
    return null;
  }

  onCancel() {
    this.dialogRef.close();
  }

}
