import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TasksService } from 'src/app/Services/Task/tasks.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';

@Component({
  selector: 'app-assign-task',
  templateUrl: './assign-task.component.html',
  styleUrls: ['./assign-task.component.css']
})
export class AssignTaskComponent implements OnInit {
  // projectCreatedBy: any;
  // projectID: any;
  // taskID: number = 0;
  // searchEmployee: string = '';

  // assignedEmployees: string[] = [];

  // employeeList: string[] = [];

  // filteredEmployeesList: string[] = [];

  // employeeTaskLimit:string[]=[];

  // showDropdown: boolean = false;

  // filteredEmployees: string[] = [];

  // constructor(
  //   @Inject(MAT_DIALOG_DATA) public data: any,
  //   private dialogRef: MatDialogRef<AssignTaskComponent>,
  //   public msg: SnackbarsService,
  //   public taskService: TasksService
  // ) {
  //   this.projectCreatedBy = data.projectCreatedBy;
  //   this.projectID = data.projectID;
  //   this.employeeList = data.employeeList;
  //   this.taskID = data.taskID;

  // }

  // ngOnInit(): void {
  //   this.getAllEmployeesList();
    
  //   this.getAssignEmployeeList();
    
  // }

  // toggleDropdown() {
  //   this.showDropdown = !this.showDropdown;
  // }

  // filterEmployees() {
  //   this.filteredEmployees = this.employeeList.filter(employee =>
  //     employee.toLowerCase().includes(this.searchEmployee.toLowerCase())
  //   );
  // }

  // selectEmployee(employee: string) {
  //   this.searchEmployee = employee;
  //   this.showDropdown = false;
  // }

  // addEmployee() {
  //   this.taskService.assignTask(this.projectCreatedBy, this.projectID, this.taskID, this.searchEmployee).subscribe(
  //     (response) => {
  //       console.log('Assign Task Response:', response);
  //       if (response == true) {
  //         this.msg.showSuccess("Task Assigned Successfully...");
  //         this.filteredEmployeeListForAssign();
  //         this.getAssignEmployeeList();
  //         this.searchEmployee = '';
  //       }
  //     },
  //     (error) => {
  //       console.log('Assign Task Error:', error);
  //       if (error.status === 404) {
  //         this.msg.showError("Failed to assign task: Task not found.");
  //       }
  //     }
  //   );
  // }

  // filteredEmployeeListForAssign() {
  //   this.taskService.filteredEmployeeListForAssign(this.employeeTaskLimit, this.projectCreatedBy, this.projectID, this.taskID).subscribe(
  //     (response) => {
  //       this.filteredEmployeesList = response;
  //     },
  //     (error) => {

  //     }
  //   )
  // }

  // getAllEmployeesList() {
  //   this.taskService.getAllFilterEmployess(this.employeeList, this.projectCreatedBy, this.projectID).subscribe(
  //     (response) => {
  //       this.employeeTaskLimit = response;
  //       this.filteredEmployeeListForAssign();
  //     },
  //     (error) => {

  //     }
  //   );
  // }


  
  // removeAssignEmployee(removeEmployee: string) {
  //   this.taskService.removeAssignEmployee(this.projectCreatedBy, this.projectID, this.taskID, removeEmployee).subscribe(
  //     (response) => {
  //       if (response === true) {
  //         this.msg.showSuccess("Employee Removed Successfully...");
  //         this.filteredEmployeeListForAssign();
  //         this.getAssignEmployeeList();
  //       }
  //     },
  //     (error) => {
  //       if (error.status === 404) {
  //         this.msg.showError("Failed to remove employee: Employee not found.");
  //       }
  //     }
  //   );
  // }

  
  // getAssignEmployeeList() {
  //   this.taskService.getAssignEmployeeList(this.projectCreatedBy, this.projectID, this.taskID).subscribe(
  //     (response) => {
  //       this.assignedEmployees = response;
  //     },
  //     (error) => {
  //       if (error.status === 404) {
  //         this.msg.showError("Employees Not Found...");
  //       }
  //     }
  //   );
  // }


  // close() {
  //   this.dialogRef.close();
  // }


  projectCreatedBy: any;
  projectID: any;
  taskID: number = 0;
  searchEmployee: string = '';

  assignedEmployees: string[] = [];

  employeeList: string[] = [];

  filteredEmployeesList: string[] = [];

  showDropdown: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<AssignTaskComponent>,
    public msg: SnackbarsService,
    public taskService: TasksService
  ) {
    this.projectCreatedBy = data.projectCreatedBy;
    this.projectID = data.projectID;
    this.employeeList = data.employeeList;
    this.taskID = data.taskID;
  }

  ngOnInit(): void {
    this.getAllEmployeesList();
    this.getAssignEmployeeList();
  }

  toggleDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  filterEmployees() {
    if (this.searchEmployee.trim() !== '') {
      this.filteredEmployeesList = this.employeeList.filter(employee =>
        employee.toLowerCase().includes(this.searchEmployee.toLowerCase()) &&
        !this.assignedEmployees.includes(employee)
      );
    } else {
      this.filteredEmployeesList = this.employeeList.filter(employee =>
        !this.assignedEmployees.includes(employee)
      );
    }
  }

  selectEmployee(employee: string) {
    this.searchEmployee = employee;
    this.showDropdown = false;
  }

  addEmployee() {
    const selectedEmployee = this.searchEmployee.trim();

    if (selectedEmployee === '') {
      this.msg.showError("Invalid employee. Please select an employee from the dropdown.");
      return;
    }
    const isEmployeeValid = this.filteredEmployeesList.includes(selectedEmployee);

    if (!isEmployeeValid) {
      this.msg.showError("Invalid employee. Please select an employee from the dropdown.");
      return;
    }

    this.taskService.assignTask(this.projectCreatedBy, this.projectID, this.taskID, selectedEmployee).subscribe(
      (response) => {
        console.log('Assign Task Response:', response);
        if (response == true) {
          this.msg.showSuccess("Task Assigned Successfully...");
          this.filteredEmployeeListForAssign();
          this.getAssignEmployeeList();
          this.searchEmployee = '';
        }
      },
      (error) => {
        console.log('Assign Task Error:', error);
        if (error.status === 404) {
          this.msg.showError("Failed to assign task: Task not found.");
        }
      }
    );
  }

  filteredEmployeeListForAssign() {
    this.taskService.filteredEmployeeListForAssign(this.employeeList, this.projectCreatedBy, this.projectID, this.taskID).subscribe(
      (response) => {
        this.filteredEmployeesList = response.filter(employee =>
          !this.assignedEmployees.includes(employee)
        );
      },
      (error) => {

      }
    )
  }

  getAllEmployeesList() {
    this.taskService.getAllFilterEmployess(this.employeeList, this.projectCreatedBy, this.projectID).subscribe(
      (response) => {
        this.employeeList = response;
        this.filteredEmployeeListForAssign();
      },
      (error) => {
        this.msg.showSuccess("failed to upload the employee list...");
      }
    );
  }

  removeAssignEmployee(removeEmployee: string) {
    this.taskService.removeAssignEmployee(this.projectCreatedBy, this.projectID, this.taskID, removeEmployee).subscribe(
      (response) => {
        if (response === true) {
          this.msg.showSuccess("Employee Removed Successfully...");
          this.filteredEmployeeListForAssign();
          this.getAssignEmployeeList();
        }
      },
      (error) => {
        if (error.status === 404) {
          this.msg.showError("Failed to remove employee: Employee not found.");
        }
      }
    );
  }

  getAssignEmployeeList() {
    this.taskService.getAssignEmployeeList(this.projectCreatedBy, this.projectID, this.taskID).subscribe(
      (response) => {
        this.assignedEmployees = response;
        this.filteredEmployeeListForAssign();
      },
      (error) => {
        if (error.status === 404) {
          this.msg.showError("Employees Not Found...");
        }
      }
    );
  }

  close() {
    this.dialogRef.close();
  }
}

