import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/Authentication/login/login.component';
import { RegisterComponent } from './components/Authentication/register/register.component';
import { ProfileComponent } from './components/Authentication/profile/profile.component';
import { AdminDashboardComponent } from './components/Admin/admin-dashboard/admin-dashboard.component';
import { ViewEmployesComponent } from './components/Admin/view-employes/view-employes.component';
import { EmployeDashboardComponent } from './components/Employee/employe-dashboard/employe-dashboard.component';
import { TaskDashBoardComponent } from './components/Employee/task-dash-board/task-dash-board.component';
import { CreateNewProjectComponent } from './components/Employee/create-new-project/create-new-project.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { authnticationGuard } from './Guard/authntication.guard';
import { RecycleBinComponent } from './components/Employee/recycle-bin/recycle-bin.component';
import { HomeComponent } from './components/Home/home/home.component';
import { ViewProjectComponent } from './components/Employee/view-project/view-project.component';
import { TestingComponent } from './testing/testing.component';
import { ContractComponent } from './components/Admin/contract/contract.component';
import { DeActivateGuard } from './Guard/de-activate-guard.guard';



const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent ,canDeactivate:[DeActivateGuard]},
  { path: 'home', component: HomeComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [authnticationGuard] },
  { path: 'Admin', component: AdminDashboardComponent, canActivate: [authnticationGuard] },
  { path: 'contract', component: ContractComponent, canActivate: [authnticationGuard] },
  { path: 'viewEmployee', component: ViewEmployesComponent, canActivate: [authnticationGuard] },
  { path: 'employeeDashboard', component: EmployeDashboardComponent, canActivate: [authnticationGuard] },
  { path: 'taskDashboard/:projectId', component: TaskDashBoardComponent, canActivate: [authnticationGuard] },
  { path: 'createNewProject', component: CreateNewProjectComponent, canActivate: [authnticationGuard] },
  { path: 'recycleBin', component: RecycleBinComponent, canActivate: [authnticationGuard] },
  { path: 'testing', component: TestingComponent },
  { path: '**', component: PageNotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
