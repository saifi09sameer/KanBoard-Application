import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { InformationService } from '../Services/Information/information.service';
import { Observable } from 'rxjs';
import { SnackbarsService } from '../Snackbars/snackbars.service';

@Injectable({
  providedIn: 'root'
})
export class authnticationGuard implements CanActivate {
  constructor(
    private information: InformationService,
    private router: Router,
    private msg: SnackbarsService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.information.isloggedin()==true) {
      if (route.url.length > 0) {
        const menu = route.url[0].path;
        const role = this.information.getrole();
        if (menu === 'viewEmployee' || menu === 'Admin' || menu === 'contract') {
          if (role === 'Admin') {
            return true;
          } else {
            this.router.navigate(['employeeDashboard']);
            this.msg.showWarning('You do not have Access.');
            return false;
          }
        } else if (menu === 'employeeDashboard' || menu.startsWith('taskDashboard') || menu === 'recycleBin') {
          if (role === 'employee') {
            return true;
          } else {
            this.router.navigate(['Admin']);
            this.msg.showError('You Are Not Employee');
            return false;
          }
        } else {
          return true;
        }

      } else {
        return true;
      }
    } else {
      this.router.navigate(['login']);
      this.msg.showError('You are not logged in.');
      return false;
    }
  }

}

