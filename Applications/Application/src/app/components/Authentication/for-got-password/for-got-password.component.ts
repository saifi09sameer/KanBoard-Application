import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-for-got-password',
  templateUrl: './for-got-password.component.html',
  styleUrls: ['./for-got-password.component.css']
})
export class ForGotPasswordComponent implements OnInit {
  emailForm: FormGroup;
  passwordForm: FormGroup;
  progressDisplay: boolean = false;
  OTP: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private authentication: AuthnticationService,
    private snackbarsService: SnackbarsService,
    private dilog: MatDialog
  ) {
    this.emailForm = this.formBuilder.group({
      employeeEmail: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z-]+\.[a-zA-Z]{2,4}')]]
    });

    this.passwordForm = this.formBuilder.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      
      otp: ['', [Validators.required, this.otpValidator.bind(this)]]
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit(): void {
  }

  sendOTP() {
    if (this.emailForm.valid) {
      const employeeEmail = this.emailForm.value.employeeEmail;
      this.authentication.sendOTP(employeeEmail).subscribe(
        (response) => {
          this.OTP = response;
          this.snackbarsService.showSuccess("OTP sent to employee's email!");
        },
        (error) => {
          if (error.status === 404) {
            this.snackbarsService.showError("Employee not found. Please enter a valid email address.");
          } else {
            this.snackbarsService.showError("Failed to send OTP. Please try again.");
          }
        }
      );
    } else {
      this.snackbarsService.showError("Please enter a valid email address.");
    }
  }


  submitForm() {
    if (this.passwordForm.valid) {
      const password = this.passwordForm.value.password;
      const employeeEmail = this.emailForm.value.employeeEmail;
      this.authentication.forgotPassword(employeeEmail, password).subscribe(
        (response) => {
          this.snackbarsService.showSuccess("Password Change Successfully... ");
          
          this.emailForm.reset();
          this.passwordForm.reset();
          this.dilog.closeAll();
        },
        (error) => {
          console.error(error);
          if (error.status === 404) {
            this.snackbarsService.showError("Employee not found....");
          }else{
            this.snackbarsService.showError("Server Error....")
          }
        }
      );
    } else {
      this.snackbarsService.showError("All fields are required.");
    }
  }
  



  passwordMatchValidator(formGroup: FormGroup) {
    const passwordControl = formGroup.get('password');
    const confirmPasswordControl = formGroup.get('confirmPassword');

    if (passwordControl?.value && confirmPasswordControl?.value) {
      if (passwordControl.value === confirmPasswordControl.value) {
        confirmPasswordControl.setErrors(null);
      } else {
        confirmPasswordControl.setErrors({ passwordMismatch: true });
      }
    }
  }

  otpValidator(control: any) {
    const enteredOTP = control.value;
    if (enteredOTP && enteredOTP.toString() !== this.OTP.toString()) {
      return { invalidOTP: true };
    }
    return null;
  }

  closeDialog() {
    this.dilog.closeAll();
  }
}
