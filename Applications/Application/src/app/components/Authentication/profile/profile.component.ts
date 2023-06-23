import { Dialog } from '@angular/cdk/dialog';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Employee } from 'src/app/Model/Employee';
import { AuthnticationService } from 'src/app/Services/Authntication/authntication.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  employee: any;
  imageFile!: File;
  maritalStatusOptions = ['Single', 'Married', 'Divorced', 'Widowed'];

  constructor(private fb: FormBuilder, private auth:AuthnticationService, private dialog:MatDialog,
    private msg:SnackbarsService) {
    this.employee = {
      employeeID: 0,
      employeeName: '',
      gender: '',
      maritalStatus: '',
      employeeDOB: '',
      employeeAadhaarNumber: 0,
      bloodGroup: '',
      employeePhoneNumber: '',
      employeeEmail: '',
      employeePassword: '',
      joiningDate: '',
      employeeRole: '',
      nationality: '',
      employeeCity: '',
      imageURL: ''
    };
  }

  ngOnInit() {
    this.createForm();
    this.loadEmployeeData();
  }

  createForm() {
    this.profileForm = this.fb.group({
      employeeID: [this.employee.employeeID],
      employeeName: [this.employee.employeeName, [Validators.required, Validators.pattern(/^[a-zA-Z][a-zA-Z0-9\s]*$/)]],
      gender: [this.employee.gender, Validators.required],
      maritalStatus: [this.employee.maritalStatus, Validators.required],
      employeeDOB: [this.employee.employeeDOB, [Validators.required, this.dateValidator]],
      employeeAadhaarNumber: [this.employee.employeeAadhaarNumber, [Validators.required, Validators.pattern(/^\d{12}$/)]],
    bloodGroup: [this.employee.bloodGroup, Validators.required],
    employeePhoneNumber: [this.employee.employeePhoneNumber, [Validators.required, Validators.pattern(/^[6-9]\d{9}$/)]],
      employeeEmail: [this.employee.employeeEmail],
      joiningDate: [this.employee.joiningDate],
      employeeRole: [this.employee.employeeRole, Validators.required],
      nationality: [this.employee.nationality],
      employeeCity: [this.employee.employeeCity, [Validators.required, Validators.pattern(/^[^0-9!@#$%^&*()]*$/)]],
      imageURL: [this.employee.imageURL]
    });
  }

  loadEmployeeData() {
    this.auth.getEmployeeDetails().subscribe(
      (response)=>{
        this.employee=response;
        this.profileForm.patchValue(this.employee);
      }
    )
      
   
    
  }

  getImageURL() {
    const imageURLControl = this.profileForm.get('imageURL');
    return (imageURLControl && imageURLControl.value) ? imageURLControl.value : 'assets/default-profile-image.jpg';
  }

  onImageUpload(event: any) {
    const files = event.target.files;
    if (files && files.length > 0) {
      this.imageFile = files[0];
      this.profileForm.patchValue({ imageURL: URL.createObjectURL(this.imageFile) });
    }
  }


  speak() {
    const speak = new SpeechSynthesisUtterance(`logout successfully`+this.employee.employeeName);
    speak.rate = 0.8;
    window.speechSynthesis.speak(speak);
  }
  

  

  onSubmit() {
    if (this.profileForm.valid) {
      const updatedEmployee: Employee = {
        employeeID: this.profileForm.get('employeeID')?.value,
        employeeName: this.profileForm.get('employeeName')?.value,
        gender: this.profileForm.get('gender')?.value,
        maritalStatus: this.profileForm.get('maritalStatus')?.value,
        employeeDOB: this.profileForm.get('employeeDOB')?.value,
        employeeAadhaarNumber: this.profileForm.get('employeeAadhaarNumber')?.value,
        bloodGroup: this.profileForm.get('bloodGroup')?.value,
        employeePhoneNumber: this.profileForm.get('employeePhoneNumber')?.value,
        employeeEmail: this.profileForm.get('employeeEmail')?.value,
        employeePassword: '',
        joiningDate: this.profileForm.get('joiningDate')?.value,
        employeeRole: this.profileForm.get('employeeRole')?.value,
        nationality: this.profileForm.get('nationality')?.value,
        employeeCity: this.profileForm.get('employeeCity')?.value,
        imageURL: this.profileForm.get('imageURL')?.value,
        status: false
      };

      this.auth.updateEmployee(updatedEmployee).subscribe(
        (response)=>{
          this.msg.showSuccess("Updated Successfully.....");
          this.dialog.closeAll();
        },
        (error)=>{

        }
      )
    }

  }
  

  dateValidator(control: any) {
    const inputDate = new Date(control.value);
    const currentDate = new Date();
    if (inputDate >= currentDate) {
      return { futureDate: true };
    }
    return null;
  }

  
}
