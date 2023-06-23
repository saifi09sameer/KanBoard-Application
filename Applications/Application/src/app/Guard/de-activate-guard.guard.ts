import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RegisterComponent } from '../components/Authentication/register/register.component';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeActivateGuard implements CanDeactivate<RegisterComponent> {
  
  canDeactivate(component: RegisterComponent, currentRoute: ActivatedRouteSnapshot, currentState: RouterStateSnapshot, nextState: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return component.canExit();
  }
  
}