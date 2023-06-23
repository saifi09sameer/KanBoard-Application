import { ChangeDetectorRef, Component, DoCheck, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { OnlineStatusService } from '../Services/Authntication/online-status.service';
import { SnackbarsService } from '../Snackbars/snackbars.service';
import { ConfirmationDialogData } from '../Model/ConfirmationDialogData';
import { ProfileComponent } from '../components/Authentication/profile/profile.component';
import { ConfirmationDialogComponent } from '../components/confirmation-dialog/confirmation-dialog.component';
import { NotificationService } from '../Services/Notification/notification.service';
import { SettingsComponent } from '../components/Authentication/settings/settings.component';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements DoCheck{
  employeeLoginName:any;
  title = 'KANBANBOARD';
  onlineEmployees: string[] = [];
  isHome:boolean=false;
  isMenuVisible = false;
  isAdmin: boolean = false;
  notifications: string[] = [];

  badgevisible = false;
  employee: any;


  currentTime?: Date;
 
  ngOnInit(): void {
    
    setInterval(() => {
      this.currentTime = new Date();
    }, 1000);
    
  }
  
  constructor(private dialog: MatDialog, private route: Router, private onlineService: OnlineStatusService,
    private cdRef: ChangeDetectorRef,
    private msg:SnackbarsService,
    private notificationService: NotificationService
    ) {
    let role = localStorage.getItem("employeeRole");

    if (role === "Admin") {
      this.isAdmin = true;
    }
  }

  Logout() {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmation',
        message: 'Are you sure you want to logout?',
        confirmText: 'Logout',
        cancelText: 'Cancel'
      } as ConfirmationDialogData,
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.onlineService.setEmployeeStatusOffline().subscribe(
          (response) => {
            if (response === true) {
              this.msg.showSuccess('Logout Successfully......');
              localStorage.clear();
              this.route.navigateByUrl('/login');
            }
          }
        );
      }
    });
  }


  // backgroundColor: string = 'black'; // Initial background color is black

  changeBackgroundColor() {
    this.backgroundColor = this.backgroundColor === 'black' ? 'white' : 'black';
  }

  backgroundColor: string = 'white'; 

  openSettings() {
    const dialogRef = this.dialog.open(SettingsComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        alert("this is after clooseing the dolog "+ JSON.stringify(result));
        this.backgroundColor = result;
        this.cdRef.detectChanges();
        alert("this is after clooseing the dolog "+ this.backgroundColor);
      }
    });
  }
  
  getAllNotifications() {
    this.notificationService.getAllNotifications().subscribe(
      (response) => {
        this.notifications = response;
      },
      (error) => {

      }
    )
  }

  getAllEmployeeStatus() {
    this.onlineService.getAllEmployeesStatus().subscribe(
      (response) => {
        this.onlineEmployees = response;
      },
      (error) => {
        console.log(error + "EmployeeNot Found");
      }
    )
  }

  number:number=0;

  ngDoCheck(): void {
    let currentroute = this.route.url;
    let role = localStorage.getItem("employeeRole");
    this.employeeLoginName = localStorage.getItem("employeeName");
    
    if (currentroute == '/login' || currentroute == '/register' || currentroute=='/home') {
      this.isMenuVisible = false
   
      
    } else {
      this.isMenuVisible = true
      
    }

    if(currentroute == '/Admin' || currentroute == '/contract' || currentroute=='/viewEmployee' || currentroute =='/employeeDashboard' || currentroute == '/recycleBin'){
      
    }else{
      if (localStorage.getItem("employeeRole") != null) {
        this.isMenuVisible = true;
      } else {
        this.isMenuVisible = false;
      }
      
    }
 


    if(currentroute=='/login' || currentroute=='/register'){
      this.isHome=true;
    }else{
      this.isHome=false;
    }

    if (role === "Admin") {
      this.isAdmin = true;
    } else {
      this.isAdmin = false;
    }

  }
 

  openProfile() {
    this.dialog.open(ProfileComponent);
  }

  badgevisibility() {
    this.badgevisible = true;
    
  }

  
  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
}
