import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { ITask } from 'src/app/Model/task';
import { TasksService } from 'src/app/Services/Task/tasks.service';
import { SnackbarsService } from 'src/app/Snackbars/snackbars.service';
@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent {
  projectID:number=0;
  projectCreatedEmployee:string="";
  
  projectForm!: FormGroup;

  constructor(private formBuilder: FormBuilder,private msg:SnackbarsService,
    private taskService:TasksService,@Inject(MAT_DIALOG_DATA) public data: any,
    private dialog:MatDialog

    ) {

    this.projectID=data.projectID;
    this.projectCreatedEmployee=data.createdBy;
   
   }


   ngOnInit() {
    this.projectForm = this.formBuilder.group({
      taskName: ['', [Validators.required, Validators.pattern(/^[A-Za-z][A-Za-z0-9\s]*$/)]],
      taskDescription: ['', Validators.required],
      taskCreatedDate: ['', [Validators.required, this.currentDateValidator]],
      taskEndDate: ['', [Validators.required, this.endDateValidator]],
      taskPriority: ['', Validators.required],
      taskStage: ['', Validators.required]
});
}

  createTask() {
    if (this.projectForm.valid) {
      
      const task: ITask = {
        taskID: this.projectForm.value.taskID,
        taskName: this.projectForm.value.taskName,
        taskDescription: this.projectForm.value.taskDescription,
        taskCreatedDate: this.projectForm.value.taskCreatedDate?.toISOString()?.split("T")[0],
        taskEndDate: this.projectForm.value.taskEndDate?.toISOString()?.split("T")[0],
        taskPriority: this.projectForm.value.taskPriority,
        taskStage: this.projectForm.value.taskStage,
        taskCreatedBY: "",
        assignTaskEmployees: [],
      };
      this.taskService.createNewTask(task,this.projectCreatedEmployee,this.projectID).subscribe(
        (response)=>{
          if(response==true){
            this.msg.showSuccess("Task Created Successfully");
            this.dialog.closeAll();
          }
          else{
            this.msg.showError("Task Not Created....");
          }
        },
        (error)=>{
          this.msg.showError("Server Error....");
        }
      )
    }
  }

  currentDateValidator(control: any) {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();

    if (selectedDate.getDate() !== currentDate.getDate() ||
      selectedDate.getMonth() !== currentDate.getMonth() ||
      selectedDate.getFullYear() !== currentDate.getFullYear()) {
      return { currentDateInvalid: true };
    }

    return null;
  }


  endDateValidator(control: any) {
    const selectedDate = new Date(control.value);
    const currentDate = new Date();
  
    // Set the time of the current date to 00:00:00 to compare only the dates
    currentDate.setHours(0, 0, 0, 0);
  
    // Compare the selected date with the current date
    if (selectedDate < currentDate && selectedDate.toDateString() !== currentDate.toDateString()) {
      return { endDateInvalid: true };
    }
  
    return null;
  }
  

}

  // projectID:number=0;
  // projectCreatedEmployee:string="";
  
  // projectForm!: FormGroup;

  // constructor(private formBuilder: FormBuilder,private msg:SnackbarsService,
  //   private taskService:TasksService,@Inject(MAT_DIALOG_DATA) public data: any,
  //   private dialog:MatDialog

  //   ) {

  //   this.projectID=data.projectID;
  //   this.projectCreatedEmployee=data.createdBy;
   
  //  }


  // ngOnInit() {
  //   this.projectForm = this.formBuilder.group({
  //     taskName: ['', Validators.required],
  //     taskDescription: ['', Validators.required],
  //     taskCreatedDate: ['', [Validators.required, this.currentDateValidator]],
  //     taskEndDate: ['', [Validators.required, this.endDateValidator]],
  //     taskPriority: ['', Validators.required],
  //     taskStage: ['', Validators.required]
  //   });
  // }

  // createTask() {
  //   if (this.projectForm.valid) {
      
  //     const task: ITask = {
  //       taskID: this.projectForm.value.taskID,
  //       taskName: this.projectForm.value.taskName,
  //       taskDescription: this.projectForm.value.taskDescription,
  //       taskCreatedDate: this.projectForm.value.taskCreatedDate?.toISOString()?.split("T")[0],
  //       taskEndDate: this.projectForm.value.taskEndDate?.toISOString()?.split("T")[0],
  //       taskPriority: this.projectForm.value.taskPriority,
  //       taskStage: this.projectForm.value.taskStage,
  //       taskCreatedBY: "",
  //       assignTaskEmployees: [],
  //     };
  //     this.taskService.createNewTask(task,this.projectCreatedEmployee,this.projectID).subscribe(
  //       (response)=>{
  //         if(response==true){
  //           this.msg.showSuccess("Task Created Successfully");
  //           this.dialog.closeAll();
  //         }
  //         else{
  //           this.msg.showError("Task Not Created....");
  //         }
  //       },
  //       (error)=>{
  //         this.msg.showError("Server Error....");
  //       }
  //     )
  //   }
  // }

  // currentDateValidator(control: any) {
  //   const selectedDate = new Date(control.value);
  //   const currentDate = new Date();

  //   if (selectedDate.getDate() !== currentDate.getDate() ||
  //     selectedDate.getMonth() !== currentDate.getMonth() ||
  //     selectedDate.getFullYear() !== currentDate.getFullYear()) {
  //     return { currentDateInvalid: true };
  //   }

  //   return null;
  // }

  // endDateValidator(control: any) {
  //   const selectedDate = new Date(control.value);
  //   const currentDate = new Date();

  //   if (selectedDate < currentDate) {
  //     return { endDateInvalid: true };
  //   }
  //   return null;
  // }



