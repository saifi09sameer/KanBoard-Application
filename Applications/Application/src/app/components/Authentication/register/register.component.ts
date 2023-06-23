import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeRegister } from 'src/app/Model/Employee';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  progressDisplay: boolean = false;
  employeeRegister: any;

  constructor(
    private formBuilder: FormBuilder,
    private authntication: AuthnticationService,
    private msg: SnackbarsService,
    private route: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.formStart();
  }

  formStart() {
    this.employeeRegister = this.formBuilder.group({
      employeeName: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]],
      employeeEmail: ['', [Validators.required, Validators.email, Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z-]+\.[a-zA-Z]{2,4}')]],
      employeePassword: ['', [Validators.required,Validators.pattern('^(?=.*[A-Z])(?=.*[@#]).*$')]],
      employeePhoneNumber: ['', [Validators.required, Validators.pattern(/^[6-9]\d{9}$/)]],
      nationality: ['', [Validators.required, Validators.pattern(/^[a-zA-Z\s]*$/)]]
    });
  }

  proceedregister() {
    this.progressDisplay = true;

    if (this.employeeRegister.valid) {
      const employeeData: EmployeeRegister = this.employeeRegister.value;
      const employee: EmployeeRegister = new EmployeeRegister(
        employeeData.employeeName,
        employeeData.employeeEmail,
        employeeData.employeePassword,
        employeeData.employeePhoneNumber,
        employeeData.nationality
      );

      this.authntication.register(employee).subscribe(
        (response) => {
          console.log('Employee registration successful', response);
          this.employeeRegister.reset();
          this.progressDisplay = false;
          this.msg.showSuccess('Employee registration successful');
          this.route.navigateByUrl('login');
        },
        (error) => {
          if (error.status === 404) {
            this.msg.showError('Employee Already Exists');
          } else if (error.status === 0) {
            this.msg.showError('Connection Error');
          }
          this.progressDisplay = false;
        }
      );
    }
  }


  canExit(): Observable<boolean> {
    const employeeName = this.employeeRegister.get('employeeName')?.value;
    const employeeEmail = this.employeeRegister.get('employeeEmail')?.value;
    const employeePassword = this.employeeRegister.get('employeePassword')?.value;
    const employeePhoneNumber = this.employeeRegister.get('employeePhoneNumber')?.value;
    const nationality = this.employeeRegister.get('nationality')?.value;
  
    if (employeeName || employeeEmail || employeePassword || employeePhoneNumber || nationality) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        width: '600px',
        data: {
          title: 'Confirmation',
          message: 'Do you really want to leave this page? All unsaved changes will be lost.',
          confirmText: 'Confirm',
          cancelText: 'Cancel',
        },
      });
  
      return dialogRef.afterClosed();
    } else {
      return of(true);
    }
  }



}