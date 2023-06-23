import { HttpClient } from '@angular/common/http';
import { Component, DoCheck } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError } from 'rxjs';
import { Contact } from 'src/app/Model/Contact';
import { AdminService } from 'src/app/Services/Authntication/admin.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  contactForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private adminService: AdminService,
    private msg:SnackbarsService
  ) { 
    this.contactForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      message: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.contactForm.valid) {
      const contact: Contact = {
        name: this.contactForm.value.name,
        email: this.contactForm.value.email,
        subject: this.contactForm.value.subject,
        message: this.contactForm.value.message,
        date: ""
      };
      this.adminService.sendMsgToAdmin(contact).subscribe(
        (response) => {
          this.msg.showSuccess("Successfully Sent Message....     Thank You...")
          this.contactForm.reset();
        },
        (error) => {
          this.msg.showInfo("Please Send After Some Time.....")
        });
    }
  }

}
