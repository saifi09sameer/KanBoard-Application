import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/Authentication/login/login.component';
import { RegisterComponent } from './components/Authentication/register/register.component';
import { ForGotPasswordComponent } from './components/Authentication/for-got-password/for-got-password.component';
import { HttpClientModule } from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatStepperModule} from '@angular/material/stepper';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatMenuModule } from '@angular/material/menu';
import { ProfileComponent } from './components/Authentication/profile/profile.component';
import { ViewEmployesComponent } from './components/Admin/view-employes/view-employes.component';
import { AdminDashboardComponent } from './components/Admin/admin-dashboard/admin-dashboard.component';
import { MatDialogRef } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { UpdateEmployeeComponent } from './components/Admin/update-employee/update-employee.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EmployeDashboardComponent } from './components/Employee/employe-dashboard/employe-dashboard.component';
import { TaskDashBoardComponent } from './components/Employee/task-dash-board/task-dash-board.component';
import { CreateNewProjectComponent } from './components/Employee/create-new-project/create-new-project.component';
import { AssignProjectComponent } from './components/Employee/assign-project/assign-project.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { CreateTaskComponent } from './components/Employee/create-task/create-task.component';
import { ToastrModule, provideToastr } from 'ngx-toastr';
import { ViewTaskComponent } from './components/Employee/view-task/view-task.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AssignTaskComponent } from './components/Employee/assign-task/assign-task.component';
import { HomeComponent } from './components/Home/home/home.component';
import { NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';
import { RecycleBinComponent } from './components/Employee/recycle-bin/recycle-bin.component';
import { ViewProjectComponent } from './components/Employee/view-project/view-project.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatTooltipModule} from '@angular/material/tooltip';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { TestingComponent } from './testing/testing.component';
import { ContractComponent } from './components/Admin/contract/contract.component';
import { NavigationComponent } from './navigation/navigation.component';
import { SettingsComponent } from './components/Authentication/settings/settings.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ForGotPasswordComponent,
    ProfileComponent,
    ViewEmployesComponent,
    AdminDashboardComponent,
    UpdateEmployeeComponent,
    EmployeDashboardComponent,
    TaskDashBoardComponent,
    CreateNewProjectComponent,
    AssignProjectComponent,
    CreateTaskComponent,
    ViewTaskComponent,
    PageNotFoundComponent,
    AssignTaskComponent,
    HomeComponent,
    RecycleBinComponent,
    ViewProjectComponent,
    ConfirmationDialogComponent,
    TestingComponent,
    ContractComponent,
    NavigationComponent,
    SettingsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatDialogModule,
    HttpClientModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    ReactiveFormsModule,
    FormsModule,
    MatChipsModule,
    CommonModule,
    MatSnackBarModule,
    DragDropModule,
    MatStepperModule,
    MatProgressBarModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatRadioModule,
    MatMenuModule,
    ToastrModule.forRoot(),
    MatTableModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatAutocompleteModule,
    MatTooltipModule,
    NgxUiLoaderModule,
    MatSidenavModule ,
    NgxUiLoaderHttpModule.forRoot({
      showForeground:true
    })
  ],
  providers: [
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
