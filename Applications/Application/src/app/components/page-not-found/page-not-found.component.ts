import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent {
  buttonText: string;

  constructor(private router: Router) {
   
    const isLoggedIn = localStorage.getItem('employeeRole');

    
    if (isLoggedIn) {
   
      if (isLoggedIn === 'Admin') {
        this.buttonText = 'Back to Admin Dashboard';
      } else {
        this.buttonText = 'Back to Dashboard';
      }
    } else {
      
      this.buttonText = 'Back to Home';
    }
  }

  goToDashboard() {
   
    const isLoggedIn = localStorage.getItem('employeeRole');

    if (isLoggedIn) {
     
      if (isLoggedIn == 'Admin') {
        this.router.navigate(['/Admin']);
      } else {
        this.router.navigate(['/employeeDashboard']);
      }
    } else {
     
      this.router.navigate(['/home']);
    }
  }
}
