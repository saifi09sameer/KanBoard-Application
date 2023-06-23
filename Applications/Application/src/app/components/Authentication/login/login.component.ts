import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Route, Router } from '@angular/router';
import { EmployeeLogin } from 'src/app/Model/Employee';
import { ForGotPasswordComponent } from '../for-got-password/for-got-password.component';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { InformationService } from 'src/app/Services/Information/information.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;
  isActiveProgress: boolean = false;
  loginForm!: FormGroup;
  responseData: any;

  constructor(private formBuilder: FormBuilder,
    private authentication: AuthnticationService,
    private router: Router,
    private dilog: MatDialog,
    private msg: SnackbarsService,
    private information: InformationService,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.createLoginForm();
  }

  createLoginForm() {
    this.loginForm = this.formBuilder.group({
      employeeEmail: ['', [Validators.required, Validators.email, Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z-]+\.[a-zA-Z]{2,4}')]],
      employeePassword: ['', [Validators.required,Validators.pattern('^(?=.*[A-Z])(?=.*[@#]).*$')]],
    });
  }

  loginEmployee() {
    if (this.loginForm.valid) {
      const loginData: EmployeeLogin = {
        employeeEmail: this.loginForm.value.employeeEmail,
        employeePassword: this.loginForm.value.employeePassword
      };
      this.isActiveProgress = true;
      this.authentication.login(loginData)
        .subscribe(
          (response) => {
            this.loginForm.reset();
            this.responseData = response;
            this.isActiveProgress = false;
            localStorage.setItem("Token", this.responseData.Token);
            localStorage.setItem("employeeRole", this.responseData.employeeRole);
            localStorage.setItem("employeeEmail", this.responseData.employeeEmail);
            localStorage.setItem("employeeName", this.responseData.employeeName);
            console.log(this.responseData.Token);
            if (this.responseData.employeeRole === "Admin") {
              this.information.setToLogin();
              this.information.setRole("Admin");
              this.router.navigateByUrl("/Admin");
              this.msg.showSuccess("Login Successful.. " + this.responseData.employeeName);
            } else {
              this.information.setToLogin();
              this.information.setRole("Employee");
              this.router.navigateByUrl("/employeeDashboard");
              this.msg.showSuccess("Login Successful..", " OK");
            }
          },
          (error) => {
            this.loginForm.reset();
            this.isActiveProgress = false;
            if (error.status === 404) {
              this.msg.showError("You are not Employeee..");
            } else {
              this.msg.showError("Connection Error");
            }
          }
        );
    }
  }

  forGotPassword() {
    this.dilog.open(ForGotPasswordComponent);
  }

}



